# 项目启动文档

## 项目名称

**Dubbo微服务商城**

## 项目背景

该项目旨在构建一个基于Java和Dubbo的微服务商城系统。通过模块化设计，将不同的业务功能拆分为独立的服务，提升系统的可维护性和扩展性。系统包含广告、购物车、货币转换、邮件通知、支付、产品目录、推荐、物流和结账等核心业务模块。

## 项目目标

项目的目标是：

1. 构建一个功能完备且可扩展的微服务商城系统。
2. 各服务模块之间通过Dubbo进行高效通信，保证系统的稳定性和高可用性。
3. 提供友好的用户界面，用户可以通过浏览器访问前端页面并进行购物操作。
4. 支持多种货币的转换和在线支付功能。

## 服务模块概述

1. **adService**: 广告服务，负责管理和展示广告内容。
2. **cartService**: 购物车服务，负责处理用户的购物车操作，如添加、删除商品等。
3. **currencyService**: 货币服务，负责提供多种货币之间的转换功能。
4. **emailService**: 邮件服务，负责发送订单确认、优惠信息等邮件通知。
5. **paymentService**: 支付服务，处理用户的在线支付操作。
6. **productCatalogsService**: 产品目录服务，管理商品的分类、信息展示等功能。
7. **recommendationService**: 推荐服务，根据用户行为推荐相关商品。
8. **shippingService**: 物流服务，处理订单的物流信息和配送服务。
9. **checkoutService**: 结账服务，汇总购物车内容，生成订单并处理结账流程。
10. **fronted**: 前端服务，提供用户界面，供用户浏览和操作。

## 服务启动顺序

为了确保系统的正确启动，各个服务的启动顺序如下：

1. 启动 `adService`
2. 启动 `cartService`
3. 启动 `currencyService`
4. 启动 `emailService`
5. 启动 `paymentService`
6. 启动 `productCatalogsService`
7. 启动 `recommendationService`
8. 启动 `shippingService`
9. 启动 `checkoutService`
10. 启动 `fronted`

**注意：模块启动顺序可调换。**

## 访问前端页面

所有服务启动完成后，可以通过以下地址访问商城的前端页面： http://localhost:9000/static

## 项目配置

### 环境要求

- JDK 17+
- Apache Dubbo 3.0+
- SpringBoot 3.0+

### 配置文件

每个服务的配置文件均位于 `src/main/resources/application.yml`，请根据实际环境调整配置信息。
