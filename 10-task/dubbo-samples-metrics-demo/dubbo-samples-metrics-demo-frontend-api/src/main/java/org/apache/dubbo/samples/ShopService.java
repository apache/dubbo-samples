/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.samples;

public interface ShopService {

    boolean register(String username, String password, String realName, String mail, String phone);

    boolean login(String username, String password);

    User getUserInfo(String username);

    boolean timeoutLogin(String username, String password);

    Item checkItem(long sku, String username);

    Item checkItemGray(long sku, String username);

    OrderDetail submitOrder(long sku, int count, String address, String phone, String receiver);
}
