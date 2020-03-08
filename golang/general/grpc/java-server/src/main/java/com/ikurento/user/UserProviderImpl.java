package com.ikurento.user;

import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;


public class UserProviderImpl extends UserProviderGrpc.UserProviderImplBase {

    @Override
    public void getUser(UserId request, io.grpc.stub.StreamObserver<User> responseObserver) {
        String id = request.getId();
        User user = User.newBuilder().setId(id)
                .setTime(Timestamp.getDefaultInstance())
                .setAge(11)
                .setName("Hello")
                .setId(id)
                .build();
        responseObserver.onNext(user);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserList(UserIdList request, StreamObserver<UserList> responseObserver){
        ProtocolStringList protocolStringList = request.getIdList();
        UserList.Builder userListBuilder = UserList.newBuilder();
        for (String id : protocolStringList) {
            User user = User.newBuilder().setId(id)
                    .setTime(Timestamp.getDefaultInstance())
                    .setAge(11)
                    .setName("Hello")
                    .setId(id)
                    .build();
            userListBuilder.addUser(user);
        }
        responseObserver.onNext(userListBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserByStream(UserId request, StreamObserver<User> responseObserver){
        String id = request.getId();
        User user1 = User.newBuilder().setId(id)
                .setTime(Timestamp.getDefaultInstance())
                .setAge(11)
                .setName("Hello 1")
                .setId(id)
                .build();
        responseObserver.onNext(user1);
        User user2 = User.newBuilder().setId(id)
                .setTime(Timestamp.getDefaultInstance())
                .setAge(12)
                .setName("Hello 2")
                .setId(id)
                .build();
        responseObserver.onNext(user2);
        responseObserver.onCompleted();
    }


    @Override
    public void getErr(UserId request, StreamObserver<User> responseObserver){
        try{
            throw new Exception("test error");
        }catch(Exception ex){
             responseObserver.onError(Status.INTERNAL.withCause(ex).asException());
        }
     }
}
