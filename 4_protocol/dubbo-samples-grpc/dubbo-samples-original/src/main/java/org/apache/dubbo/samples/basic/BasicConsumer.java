/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.samples.basic;

import org.apache.dubbo.samples.basic.impl.routeguide.RouteGuideUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.protobuf.Message;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.DubboGreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.Rectangle;
import io.grpc.examples.routeguide.DubboRouteGuideGrpc;
import io.grpc.examples.routeguide.RouteNote;
import io.grpc.examples.routeguide.RouteSummary;
import io.grpc.stub.StreamObserver;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicConsumer {

    private static DubboRouteGuideGrpc.IRouteGuide routeGuide;

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring/dubbo-demo-consumer.xml");
        context.start();

        /**
         * greeter sample
         */
        DubboGreeterGrpc.IGreeter greeter = (DubboGreeterGrpc.IGreeter) context.getBean("greeter");

        System.out.println("-------- Start simple unary call test -------- ");
        HelloReply reply = greeter.sayHello(HelloRequest.newBuilder().setName("world!").build());
        System.out.println("Result: " + reply.getMessage());
        System.out.println("-------- End simple unary call test -------- \n\n\n");

        /**
         * route guide sample
         */
        DubboRouteGuideGrpc.IRouteGuide routeGuide = (DubboRouteGuideGrpc.IRouteGuide) context.getBean("routeguide");
        RouteGuideClient streamClient = new RouteGuideClient(routeGuide);
        System.out.println("-------- Start stream call test -------- ");
        try {
            List<Feature> features;
            try {
                features = RouteGuideUtil.parseFeatures(RouteGuideUtil.getDefaultFeaturesFile());
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
            // Looking for a valid feature
            streamClient.getFeature(409146138, -746188906);

            // Feature missing.
            streamClient.getFeature(0, 0);

            // Looking for features between 40, -75 and 42, -73.
            streamClient.listFeatures(400000000, -750000000, 420000000, -730000000);

            // Record a few randomly selected points from the features file.
            streamClient.recordRoute(features, 10);

            // Send and receive some notes.
            CountDownLatch finishLatch = streamClient.routeChat();

            if (!finishLatch.await(1, TimeUnit.MINUTES)) {
                streamClient.warning("routeChat can not finish within 1 minutes");
            }
        } finally {
            //
        }
        System.out.println("-------- End stream call test -------- \n");
        System.in.read();
    }

    /**
     * Sample client code that makes gRPC calls to the server.
     */
    public static class RouteGuideClient {
        private static final Logger logger = Logger.getLogger(RouteGuideClient.class.getName());

        private Random random = new Random();
        private TestHelper testHelper;

        private DubboRouteGuideGrpc.IRouteGuide routeGuide;

        /**
         * Construct client for accessing RouteGuide server using the existing channel.
         */
        public RouteGuideClient(DubboRouteGuideGrpc.IRouteGuide routeGuide) {
            this.routeGuide = routeGuide;
        }

        /**
         * Blocking unary call example.  Calls getFeature and prints the response.
         */
        public void getFeature(int lat, int lon) {
            info("*** GetFeature: lat={0} lon={1}", lat, lon);

            Point request = Point.newBuilder().setLatitude(lat).setLongitude(lon).build();

            Feature feature;
            try {
                feature = routeGuide.getFeature(request);
                if (testHelper != null) {
                    testHelper.onMessage(feature);
                }
            } catch (StatusRuntimeException e) {
                warning("RPC failed: {0}", e.getStatus());
                if (testHelper != null) {
                    testHelper.onRpcError(e);
                }
                return;
            }
            if (RouteGuideUtil.exists(feature)) {
                info("Found feature called \"{0}\" at {1}, {2}",
                        feature.getName(),
                        RouteGuideUtil.getLatitude(feature.getLocation()),
                        RouteGuideUtil.getLongitude(feature.getLocation()));
            } else {
                info("Found no feature at {0}, {1}",
                        RouteGuideUtil.getLatitude(feature.getLocation()),
                        RouteGuideUtil.getLongitude(feature.getLocation()));
            }
        }

        /**
         * Blocking server-streaming example. Calls listFeatures with a rectangle of interest. Prints each
         * response feature as it arrives.
         */
        public void listFeatures(int lowLat, int lowLon, int hiLat, int hiLon) {
            info("*** ListFeatures: lowLat={0} lowLon={1} hiLat={2} hiLon={3}", lowLat, lowLon, hiLat,
                    hiLon);

            Rectangle request =
                    Rectangle.newBuilder()
                            .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
                            .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
            Iterator<Feature> features;
            try {
                features = routeGuide.listFeatures(request);
                for (int i = 1; features.hasNext(); i++) {
                    Feature feature = features.next();
                    info("Result #" + i + ": {0}", feature);
                    if (testHelper != null) {
                        testHelper.onMessage(feature);
                    }
                }
            } catch (StatusRuntimeException e) {
                warning("RPC failed: {0}", e.getStatus());
                if (testHelper != null) {
                    testHelper.onRpcError(e);
                }
            }
        }

        /**
         * Async client-streaming example. Sends {@code numPoints} randomly chosen points from {@code
         * features} with a variable delay in between. Prints the statistics when they are sent from the
         * server.
         */
        public void recordRoute(List<Feature> features, int numPoints) throws InterruptedException {
            info("*** RecordRoute");
            final CountDownLatch finishLatch = new CountDownLatch(1);
            StreamObserver<RouteSummary> responseObserver = new StreamObserver<RouteSummary>() {
                @Override
                public void onNext(RouteSummary summary) {
                    info("Finished trip with {0} points. Passed {1} features. "
                                    + "Travelled {2} meters. It took {3} seconds.", summary.getPointCount(),
                            summary.getFeatureCount(), summary.getDistance(), summary.getElapsedTime());
                    if (testHelper != null) {
                        testHelper.onMessage(summary);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    warning("RecordRoute Failed: {0}", Status.fromThrowable(t));
                    if (testHelper != null) {
                        testHelper.onRpcError(t);
                    }
                    finishLatch.countDown();
                }

                @Override
                public void onCompleted() {
                    info("Finished RecordRoute");
                    finishLatch.countDown();
                }
            };

            StreamObserver<Point> requestObserver = routeGuide.recordRoute(responseObserver);
            try {
                // Send numPoints points randomly selected from the features list.
                for (int i = 0; i < numPoints; ++i) {
                    int index = random.nextInt(features.size());
                    Point point = features.get(index).getLocation();
                    info("Visiting point {0}, {1}", RouteGuideUtil.getLatitude(point),
                            RouteGuideUtil.getLongitude(point));
                    requestObserver.onNext(point);
                    // Sleep for a bit before sending the next one.
                    Thread.sleep(random.nextInt(1000) + 500);
                    if (finishLatch.getCount() == 0) {
                        // RPC completed or errored before we finished sending.
                        // Sending further requests won't error, but they will just be thrown away.
                        return;
                    }
                }
            } catch (RuntimeException e) {
                // Cancel RPC
                requestObserver.onError(e);
                throw e;
            }
            // Mark the end of requests
            requestObserver.onCompleted();

            // Receiving happens asynchronously
            if (!finishLatch.await(1, TimeUnit.MINUTES)) {
                warning("recordRoute can not finish within 1 minutes");
            }
        }

        /**
         * Bi-directional example, which can only be asynchronous. Send some chat messages, and print any
         * chat messages that are sent from the server.
         */
        public CountDownLatch routeChat() {
            info("*** RouteChat");
            final CountDownLatch finishLatch = new CountDownLatch(1);
            StreamObserver<RouteNote> requestObserver =
                    routeGuide.routeChat(new StreamObserver<RouteNote>() {
                        @Override
                        public void onNext(RouteNote note) {
                            info("Got message \"{0}\" at {1}, {2}", note.getMessage(), note.getLocation()
                                    .getLatitude(), note.getLocation().getLongitude());
                            if (testHelper != null) {
                                testHelper.onMessage(note);
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            warning("RouteChat Failed: {0}", Status.fromThrowable(t));
                            if (testHelper != null) {
                                testHelper.onRpcError(t);
                            }
                            finishLatch.countDown();
                        }

                        @Override
                        public void onCompleted() {
                            info("Finished RouteChat");
                            finishLatch.countDown();
                        }
                    });

            try {
                RouteNote[] requests =
                        {newNote("First message", 0, 0), newNote("Second message", 0, 1),
                                newNote("Third message", 1, 0), newNote("Fourth message", 1, 1)};

                for (RouteNote request : requests) {
                    info("Sending message \"{0}\" at {1}, {2}", request.getMessage(), request.getLocation()
                            .getLatitude(), request.getLocation().getLongitude());
                    requestObserver.onNext(request);
                }
            } catch (RuntimeException e) {
                // Cancel RPC
                requestObserver.onError(e);
                throw e;
            }
            // Mark the end of requests
            requestObserver.onCompleted();

            // return the latch while receiving happens asynchronously
            return finishLatch;
        }

        private void info(String msg, Object... params) {
            logger.log(Level.INFO, msg, params);
        }

        private void warning(String msg, Object... params) {
            logger.log(Level.WARNING, msg, params);
        }

        private RouteNote newNote(String message, int lat, int lon) {
            return RouteNote.newBuilder().setMessage(message)
                    .setLocation(Point.newBuilder().setLatitude(lat).setLongitude(lon).build()).build();
        }

        /**
         * Only used for unit test, as we do not want to introduce randomness in unit test.
         */
        @VisibleForTesting
        void setRandom(Random random) {
            this.random = random;
        }

        /**
         * Only used for helping unit test.
         */
        @VisibleForTesting
        interface TestHelper {
            /**
             * Used for verify/inspect message received from server.
             */
            void onMessage(Message message);

            /**
             * Used for verify/inspect error received from server.
             */
            void onRpcError(Throwable exception);
        }

        @VisibleForTesting
        void setTestHelper(TestHelper testHelper) {
            this.testHelper = testHelper;
        }
    }
}
