# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

cd ../dubbo-shop
mvn clean package -Dmaven.test.skip=true
cd -

mv ../dubbo-shop/dubbo-shop-service-ads/target/dubbo-shop-service-ads-*.jar ./apps/service/ads/app.jar
mv ../dubbo-shop/dubbo-shop-service-goodsDetails/target/dubbo-shop-service-goodsDetails-*.jar ./apps/service/goods/app.jar
mv ../dubbo-shop/dubbo-shop-service-checkout/target/dubbo-shop-service-checkout-*.jar ./apps/service/checkout/app.jar
mv ../dubbo-shop/dubbo-shop-service-hotGoods/target/dubbo-shop-service-hotGoods-*.jar ./apps/service/hotGoods/app.jar
mv ../dubbo-shop/dubbo-shop-service-newGoods/target/dubbo-shop-service-newGoods-*.jar ./apps/service/newGoods/app.jar

mv ../dubbo-shop/dubbo-shop-web-ads/target/dubbo-shop-web-ads-*.jar ./apps/web/ads/app.jar
mv ../dubbo-shop/dubbo-shop-web-goodsDetails/target/dubbo-shop-web-goodsDetails-*.jar ./apps/web/goods/app.jar
mv ../dubbo-shop/dubbo-shop-web-checkout/target/dubbo-shop-web-checkout-*.jar ./apps/web/checkout/app.jar
mv ../dubbo-shop/dubbo-shop-web-hotGoods/target/dubbo-shop-web-hotGoods-*.jar ./apps/web/hotGoods/app.jar
mv ../dubbo-shop/dubbo-shop-web-newGoods/target/dubbo-shop-web-newGoods-*.jar ./apps/web/newGoods/app.jar

cd ../onlineShopping
if [ -d "./apps/nginx/html" ]; then
  rm -rf html/*
fi
npm install && npm run build
cd -

mv ../onlineShopping/dist ./nginx/html
