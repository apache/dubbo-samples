package main

import (
	"context"
	"fmt"
)

import (
	perrors "github.com/pkg/errors"
	"github.com/dubbogo/gost/log"
)

import (
	"github.com/apache/dubbo-go/config"
)

func init() {
	config.SetProviderService(new(UserProvider))
}

type UserProvider struct {
}

func (u *UserProvider) getUser(userId string) (*User, error) {
	if user, ok := userMap[userId]; ok {
		return &user, nil
	}

	return nil, fmt.Errorf("invalid user id:%s", userId)
}

func (u *UserProvider) GetUser(ctx context.Context, req []interface{}, rsp *User) error {
	var (
		err  error
		user *User
	)

	gxlog.CInfo("req:%#v", req)
	user, err = u.getUser(req[0].(string))
	if err == nil {
		*rsp = *user
		gxlog.CInfo("rsp:%#v", rsp)
	}
	return err
}

func (u *UserProvider) GetUser0(id string, name string, age int) (*User, error) {
	var err error

	gxlog.CInfo("id:%s, name:%s, age:%d", id, name, age)
	user, err := u.getUser(id)
	if err != nil {
		return &User{}, err
	}
	if user.Name != name {
		return &User{}, perrors.New("name is not " + user.Name)
	}
	if user.Age != age {
		return &User{}, perrors.New(fmt.Sprintf("age is not %d", user.Age))
	}
	return user, err
}

func (u *UserProvider) GetUser3() error {
	return nil
}

func (u *UserProvider) GetUsers(req []interface{}) ([]User, error) {
	var err error

	gxlog.CInfo("req:%s", req)
	t := req[0].(map[string]interface{})
	user, err := u.getUser(t["ID"].(string))
	if err != nil {
		return nil, err
	}
	gxlog.CInfo("user:%v", user)

	return []User{*user}, err
}

func (u *UserProvider) GetUser1(req []interface{}) (*User, error) {
	err := perrors.New("test error")
	return nil, err
}

func (u *UserProvider) Reference() string {
	return "UserProvider"
}
