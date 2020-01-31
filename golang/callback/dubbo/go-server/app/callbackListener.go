package main

type CallbackListener struct {
}

func (*CallbackListener) changed(msg string) {
	println("callback:%s", msg)
}

func (CallbackListener) JavaClassName() string {
	return "com.ikurento.callback.listener"
}
