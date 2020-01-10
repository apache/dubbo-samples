### 1.Use Async Feature

Implement this interface

```golang
//AsyncCallbackService callback interface for async
type AsyncCallbackService interface {
	CallBack(response CallbackResponse) // callback
}
```

like this

```golang
type UserProvider struct {
	GetUser func(ctx context.Context, req []interface{}, rsp *User) error
}

func (u *UserProvider) CallBack(res common.CallbackResponse) {
	fmt.Println("CallBack res:",res)
}
```

Dubbogo will run Async logic once network request finished.

### 2.Run java server & java client following [README](https://github.com/dubbogo/dubbogo-samples/blob/master/README.md)（You must run java program to initialize configuration in zookeeper）
 
### 3.Run java server & go client 

Then start go client following [README](https://github.com/dubbogo/dubbogo-samples/blob/master/README.md).

### 4.Run go server

