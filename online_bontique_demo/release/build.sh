# build adService
docker build --build-arg APP_DIR=adService --build-arg APP_PORT=20880 --build-arg APP_FILE=adService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-ad-service:1.0 ../

# build cartService
docker build --build-arg APP_DIR=cartService --build-arg APP_PORT=20881 --build-arg APP_FILE=cartService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-cart-service:1.0 ../

# build checkoutService
docker build --build-arg APP_DIR=checkoutService --build-arg APP_PORT=20882 --build-arg APP_FILE=checkoutService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-checkout-service:1.0 ../

# build currencyService
docker build --build-arg APP_DIR=currencyService --build-arg APP_PORT=20883 --build-arg APP_FILE=currencyService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-currency-service:1.0 ../

# build emailService
docker build --build-arg APP_DIR=emailService --build-arg APP_PORT=20884 --build-arg APP_FILE=emailService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-email-service:1.0 ../

# build frontend
docker build --build-arg APP_DIR=frontend --build-arg APP_PORT=9000 --build-arg APP_FILE=frontend-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-frontend-service:1.0 ../

# build paymentService
docker build --build-arg APP_DIR=paymentService --build-arg APP_PORT=20885 --build-arg APP_FILE=paymentService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-payment-service:1.0 ../

# build productCatalogsService
docker build --build-arg APP_DIR=productCatalogsService --build-arg APP_PORT=20886 --build-arg APP_FILE=productCatalogsService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-productcatalog-service:1.0 ../

# build recommendationService
docker build --build-arg APP_DIR=recommendationService --build-arg APP_PORT=20887 --build-arg APP_FILE=recommendationService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-recommendation-service:1.0 ../

# build shippingService
docker build --build-arg APP_DIR=shippingService --build-arg APP_PORT=20888 --build-arg APP_FILE=shippingService-1.0-SNAPSHOT.jar -f ../Dockerfile -t ken222/online-boutique-shipping-service:1.0 ../

docker push ken222/online-boutique-ad-service:1.0
docker push ken222/online-boutique-cart-service:1.0
docker push ken222/online-boutique-checkout-service:1.0
docker push ken222/online-boutique-currency-service:1.0
docker push ken222/online-boutique-email-service:1.0
docker push ken222/online-boutique-frontend-service:1.0
docker push ken222/online-boutique-payment-service:1.0
docker push ken222/online-boutique-productcatalog-service:1.0
docker push ken222/online-boutique-recommendation-service:1.0
docker push ken222/online-boutique-shipping-service:1.0


