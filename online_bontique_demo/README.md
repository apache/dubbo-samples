```shell
docker build --build-arg APP_FILE=adService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-ad-service:1.0 .
docker build --build-arg APP_FILE=cartService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-cart-service:1.0 .
docker build --build-arg APP_FILE=checkoutService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-checkout-service:1.0 .
docker build --build-arg APP_FILE=currencyService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-currency-service:1.0 .
docker build --build-arg APP_FILE=emailService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-email-service:1.0 .
docker build --build-arg APP_FILE=frontendService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-frontend-service:1.0 .
docker build --build-arg APP_FILE=paymentService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-payment-service:1.0 .
docker build --build-arg APP_FILE=productCatalogsService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-productCatalog-service:1.0 .
docker build --build-arg APP_FILE=recommendationService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-ad-service:1.0 .
docker build --build-arg APP_FILE=shippingService-1.0-SNAPSHOT.jar -t sca-registry.cn-hangzhou.cr.aliyuncs.com/dubbo/online-boutique-ad-service:1.0 .
```
