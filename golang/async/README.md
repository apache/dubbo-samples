### Use Async Feature

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
