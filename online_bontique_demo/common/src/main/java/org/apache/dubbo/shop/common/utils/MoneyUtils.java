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

package org.apache.dubbo.shop.common.utils;

import org.apache.dubbo.shop.common.pojo.Money;

public class MoneyUtils {

    public static Money sum(Money a, Money b) {
        if (!isValid(a) || !isValid(b)) {
            throw new IllegalArgumentException("Invalid money value");
        } else if (!a.getCurrencyCode().equals(b.getCurrencyCode())) {
            throw new IllegalArgumentException("Mismatching currency codes");
        }

        long units = a.getUnits() + b.getUnits();
        int nanos = a.getNanos() + b.getNanos();

        if ((units >= 0 && nanos >= 0) || (units < 0 && nanos <= 0)) {
            units += nanos / 1_000_000_000;
            nanos %= 1_000_000_000;
        } else {
            if (units > 0) {
                units--;
                nanos += 1_000_000_000;
            } else {
                units++;
                nanos -= 1_000_000_000;
            }
        }
        return new Money(a.getCurrencyCode(), units, nanos);
    }

    public static Money reset(Money money){
        money.setNanos(0);
        money.setUnits(0L);
        return money;
    }
    public static Money multiplySlow(Money money, int multiplier) {
        Money result = money;
        for (int i = 1; i < multiplier; i++) {
            result = sum(result, money);
        }
        return result;
    }

    public static Boolean isValid(Money money) {
        return signMatches(money) && validNanos(money.getNanos());
    }

    private static Boolean signMatches(Money money) {
        return money.getNanos() == 0 || money.getUnits() == 0 || (money.getNanos() < 0) == (money.getUnits() < 0);
    }

    private static Boolean validNanos(Integer nanos) {
        return -999_999_999 <= nanos && nanos <= 999_999_999;
    }
}
