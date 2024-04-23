<script setup>
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
</script>


<template>
  <div class="xtx-pay-page">
    <div class="container">
      <!-- 付款信息 -->
      <div class="pay-info">
        <span class="icon iconfont icon-queren2"></span>
<!--        <div class="tip">-->
<!--          <p>订单提交成功！请尽快完成支付。</p>-->
<!--          <p>支付还剩 <span>24分30秒</span>, 超时后将取消订单</p>-->
<!--        </div>-->
        <div class="amount">
          <span>支付完成，感谢惠顾</span>
<!--          <span>¥{{ payInfo.payMoney?.toFixed(2) }}</span>-->
        </div>
<!--      </div>-->
      <!-- 付款方式 -->
<!--      <div class="pay-type">-->
<!--        <p class="head">选择以下支付方式付款</p>-->
<!--        <div class="item">-->
<!--          <p>支付平台</p>-->
<!--          <a class="btn wx" href="javascript:;"></a>-->
<!--          <a class="btn alipay" :href="payUrl"></a>-->
<!--        </div>-->
<!--        <div class="item">-->
<!--          <p>支付方式</p>-->
<!--          <a class="btn" href="javascript:;">招商银行</a>-->
<!--          <a class="btn" href="javascript:;">工商银行</a>-->
<!--          <a class="btn" href="javascript:;">建设银行</a>-->
<!--          <a class="btn" href="javascript:;">农业银行</a>-->
<!--          <a class="btn" href="javascript:;">交通银行</a>-->
<!--        </div>-->
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.xtx-pay-page {
  margin-top: 20px;
}

.pay-info {

  background: #fff;
  display: flex;
  align-items: center;
  height: 240px;
  padding: 0 80px;

  .icon {
    font-size: 80px;
    color: #1dc779;
  }

  .tip {
    padding-left: 10px;
    flex: 1;

    p {
      &:first-child {
        font-size: 20px;
        margin-bottom: 5px;
      }

      &:last-child {
        color: #999;
        font-size: 16px;
      }
    }
  }

  .amount {
    span {
      &:first-child {
        font-size: 16px;
        color: #999;
      }

      &:last-child {
        color: $priceColor;
        font-size: 20px;
      }
    }
  }
}

.pay-type {
  margin-top: 20px;
  background-color: #fff;
  padding-bottom: 70px;

  p {
    line-height: 70px;
    height: 70px;
    padding-left: 30px;
    font-size: 16px;

    &.head {
      border-bottom: 1px solid #f5f5f5;
    }
  }

  .btn {
    width: 150px;
    height: 50px;
    border: 1px solid #e4e4e4;
    text-align: center;
    line-height: 48px;
    margin-left: 30px;
    color: #666666;
    display: inline-block;

    &.active,
    &:hover {
      border-color: $xtxColor;
    }

    &.alipay {
      background: url(https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/7b6b02396368c9314528c0bbd85a2e06.png) no-repeat center / contain;
    }

    &.wx {
      background: url(https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/c66f98cff8649bd5ba722c2e8067c6ca.jpg) no-repeat center / contain;
    }
  }
}
</style>
