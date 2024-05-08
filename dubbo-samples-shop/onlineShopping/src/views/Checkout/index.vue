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
import { getCheckInfoAPI } from '@/apis/checkout'
import { useCartStore } from '@/stores/cartStore'
const cartStore = useCartStore()
// 获取结算信息
const checkInfo = ref({}) // 订单对象
const curAddress = ref({}) // 默认地址
const getCheckInfo = async () => {


  const res = await getCheckInfoAPI()
  console.log(res.data)
  checkInfo.value = res.data
  console.log('这是checkInfo.value')
  console.log(checkInfo.value)
  // 适配默认地址
  // 从地址列表中筛选出来 isDefault === 0 那一项
  // const item = checkInfo.value.userAddresses.find(item => item.isDefault === 0)
  // curAddress.value = item
}
onMounted(() => getCheckInfo())
</script>

<template>
  <div class="xtx-pay-checkout-page">
    <div class="container">
      <div class="wrapper">
        <!-- 收货地址 -->
<!--        <h3 class="box-title">收货地址</h3>-->
<!--        <div class="box-body">-->
<!--          <div class="address">-->
<!--            <div class="text">-->
<!--              <div class="none" v-if="!curAddress">您需要先添加收货地址才可提交订单。</div>-->
<!--              <ul v-else>-->
<!--                <li><span>收<i />货<i />人：</span>{{ curAddress.receiver }}</li>-->
<!--                <li><span>联系方式：</span>{{ curAddress.contact }}</li>-->
<!--                <li><span>收货地址：</span>{{ curAddress.fullLocation }} {{ curAddress.address }}</li>-->
<!--              </ul>-->
<!--            </div>-->
<!--            <div class="action">-->
<!--              <el-button size="large" @click="">切换地址</el-button>-->
<!--              <el-button size="large" @click="">添加地址</el-button>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
        <!-- 商品信息 -->
        <h3 class="box-title">商品信息</h3>
        <div class="box-body">
          <table class="goods">
            <thead>
            <tr>
              <th width="520">商品信息</th>
              <th width="170">单价</th>
              <th width="170">数量</th>
              <th width="170">小计</th>
              <th width="170">实付</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="i in checkInfo.goods" :key="i.id">
              <td>
                <a href="javascript:;" class="info">
                  <img :src="i.picture" alt="">
                  <div class="right">
                    <p>{{ i.name }}</p>
                    <p>{{ i.attrsText }}</p>
                  </div>
                </a>
              </td>
              <td>&yen;{{ i.price }}</td>
              <td>{{ i.count }}</td>
              <td>&yen;{{ i.totalPrice }}</td>
              <td>&yen;{{ i.totalPayPrice }}</td>
            </tr>
<!--            <tr>-->
<!--              <td>-->
<!--                <img src="/assets/images/liuying.png">-->
<!--              </td>-->
<!--            </tr>-->
            </tbody>
          </table>
        </div>
        <!-- 配送时间 -->
<!--        <h3 class="box-title">配送时间</h3>-->
<!--        <div class="box-body">-->
<!--          <a class="my-btn active" href="javascript:;">不限送货时间：周一至周日</a>-->
<!--          <a class="my-btn" href="javascript:;">工作日送货：周一至周五</a>-->
<!--          <a class="my-btn" href="javascript:;">双休日、假日送货：周六至周日</a>-->
<!--        </div>-->
        <!-- 支付方式 -->
<!--        <h3 class="box-title">支付方式</h3>-->
<!--        <div class="box-body">-->
<!--          <a class="my-btn active" href="javascript:;">在线支付</a>-->
<!--          <a class="my-btn" href="javascript:;">货到付款</a>-->
<!--          <span style="color:#999">货到付款需付5元手续费</span>-->
<!--        </div>-->
        <!-- 金额明细 -->
        <h3 class="box-title">金额明细</h3>
        <div class="box-body">
          <div class="total">
            <dl>
              <dt>商品件数：</dt>
              <dd>{{ checkInfo.summary?.goodsCount }}件</dd>
            </dl>
            <dl>
              <dt>商品总价：</dt>
              <dd>¥{{ checkInfo.summary?.totalPrice.toFixed(2) }}</dd>
            </dl>
            <dl>
              <dt>运<i></i>费：</dt>
              <dd>¥{{ checkInfo.summary?.postFee.toFixed(2) }}</dd>
            </dl>
            <dl>
              <dt>应付总额：</dt>
              <dd class="price">{{ checkInfo.summary?.totalPayPrice.toFixed(2) }}</dd>
            </dl>
          </div>
        </div>
        <!-- 提交订单 -->
        <div class="submit">
          <el-button type="primary" size="large" @click="$router.push('/pay');cartStore.cartList=[]">提交订单</el-button>
        </div>
      </div>
    </div>
  </div>
  <!-- 切换地址 -->
  <!-- 添加地址 -->
</template>

<style scoped lang="scss">
.xtx-pay-checkout-page {
  margin-top: 20px;

  .wrapper {
    background: #fff;
    padding: 0 20px;

    .box-title {
      font-size: 16px;
      font-weight: normal;
      padding-left: 10px;
      line-height: 70px;
      border-bottom: 1px solid #f5f5f5;
    }

    .box-body {
      padding: 20px 0;
    }
  }
}

.address {
  border: 1px solid #f5f5f5;
  display: flex;
  align-items: center;

  .text {
    flex: 1;
    min-height: 90px;
    display: flex;
    align-items: center;

    .none {
      line-height: 90px;
      color: #999;
      text-align: center;
      width: 100%;
    }

    >ul {
      flex: 1;
      padding: 20px;

      li {
        line-height: 30px;

        span {
          color: #999;
          margin-right: 5px;

          >i {
            width: 0.5em;
            display: inline-block;
          }
        }
      }
    }

    >a {
      color: $xtxColor;
      width: 160px;
      text-align: center;
      height: 90px;
      line-height: 90px;
      border-right: 1px solid #f5f5f5;
    }
  }

  .action {
    width: 420px;
    text-align: center;

    .btn {
      width: 140px;
      height: 46px;
      line-height: 44px;
      font-size: 14px;

      &:first-child {
        margin-right: 10px;
      }
    }
  }
}

.goods {
  width: 100%;
  border-collapse: collapse;
  border-spacing: 0;

  .info {
    display: flex;
    text-align: left;

    img {
      width: 70px;
      height: 70px;
      margin-right: 20px;
    }

    .right {
      line-height: 24px;

      p {
        &:last-child {
          color: #999;
        }
      }
    }
  }

  tr {
    th {
      background: #f5f5f5;
      font-weight: normal;
    }

    td,
    th {
      text-align: center;
      padding: 20px;
      border-bottom: 1px solid #f5f5f5;

      &:first-child {
        border-left: 1px solid #f5f5f5;
      }

      &:last-child {
        border-right: 1px solid #f5f5f5;
      }
    }
  }
}

.my-btn {
  width: 228px;
  height: 50px;
  border: 1px solid #e4e4e4;
  text-align: center;
  line-height: 48px;
  margin-right: 25px;
  color: #666666;
  display: inline-block;

  &.active,
  &:hover {
    border-color: $xtxColor;
  }
}

.total {
  dl {
    display: flex;
    justify-content: flex-end;
    line-height: 50px;

    dt {
      i {
        display: inline-block;
        width: 2em;
      }
    }

    dd {
      width: 240px;
      text-align: right;
      padding-right: 70px;

      &.price {
        font-size: 20px;
        color: $priceColor;
      }
    }
  }
}

.submit {
  text-align: right;
  padding: 60px;
  border-top: 1px solid #f5f5f5;
}

.addressWrapper {
  max-height: 500px;
  overflow-y: auto;
}

.text {
  flex: 1;
  min-height: 90px;
  display: flex;
  align-items: center;

  &.item {
    border: 1px solid #f5f5f5;
    margin-bottom: 10px;
    cursor: pointer;

    &.active,
    &:hover {
      border-color: $xtxColor;
      background: lighten($xtxColor, 50%);
    }

    >ul {
      padding: 10px;
      font-size: 14px;
      line-height: 30px;
    }
  }
}
</style>
