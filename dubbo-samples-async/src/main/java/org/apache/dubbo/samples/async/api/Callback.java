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

package org.apache.dubbo.samples.async.api;

public class Callback {

    public void onreturn(String msg, String arg) {
        System.out.println("onNotify:" + msg + "; args:" + arg);
    }

    public void onthrow(Throwable ex, String arg) {
        ex.printStackTrace();
        System.out.println(arg);

    }

    public void oninvoke(String arg) {
        System.out.println(arg);
    }

}
