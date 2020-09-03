package main

import (
	"context"
	"fmt"
	"strconv"
)

import (
	"github.com/dubbogo/gost/log"
	perrors "github.com/pkg/errors"
)

import (
	"github.com/apache/dubbo-go/config"
)

func init() {
	config.SetProviderService(new(UserProvider2))
}

type UserProvider2 struct {
}

func (u *UserProvider2) getUser(userId string) (*User, error) {
	if user, ok := userMap[userId]; ok {
		return &user, nil
	}

	return nil, fmt.Errorf("invalid user id:%s", userId)
}

func (u *UserProvider2) GetUser(ctx context.Context, req []interface{}, rsp *User) error {
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

func (u *UserProvider2) GetUser0(id string, name string) (User, error) {
	var err error

	gxlog.CInfo("id:%s, name:%s", id, name)
	user, err := u.getUser(id)
	if err != nil {
		return User{}, err
	}
	if user.Name != name {
		return User{}, perrors.New("name is not " + user.Name)
	}
	return *user, err
}

func (u *UserProvider2) GetUser2(ctx context.Context, req []interface{}, rsp *User) error {
	var err error

	gxlog.CInfo("req:%#v", req)
	rsp.Id = strconv.FormatFloat(req[0].(float64), 'f', 0, 64)
	rsp.Sex = Gender(MAN).String()
	return err
}

func (u *UserProvider2) GetUser3() error {
	return nil
}

func (u *UserProvider2) GetUsers(req []interface{}) ([]User, error) {
	var err error

	gxlog.CInfo("req:%s", req)
	t := req[0].([]interface{})
	user, err := u.getUser(t[0].(string))
	if err != nil {
		return nil, err
	}
	gxlog.CInfo("user:%v", user)

	return []User{*user}, err
}

func (s *UserProvider2) MethodMapper() map[string]string {
	return map[string]string{
		"GetUser2": "getUser",
	}
}

func (u *UserProvider2) Reference() string {
	return "UserProvider2"
}
