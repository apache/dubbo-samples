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
import HeaderCart from "@/views/Layout/components/HeaderCart.vue";
// import {useCategoryStore} from "@/stores/categoryStore";
// const categoryStore = useCategoryStore();
</script>

<template>
  <header class='app-header'>
    <div class="container">
      <h1 class="logo">
        <RouterLink to="/">旺旺商城</RouterLink>
      </h1>
<!--      这个是旧的没有路由分页-->
      <ul class="app-header-nav">
        <li class="home">
          <RouterLink to="/">首页</RouterLink>
        </li>
<!--        <li v-for="item in categoryStore.categoryList" :key="item.id">-->
<!--          <RouterLink active-class="active" :to="`/category/${item.id}`">{{item.name}}</RouterLink>-->
<!--        </li>-->
      </ul>
<!--      <ul class="app-header-nav">-->
<!--        <li class="home">-->
<!--          <RouterLink to="/">首页</RouterLink>-->
<!--        </li>-->
<!--        <li v-for="item in categoryStore.categoryList" :key="item.id">-->
<!--          <RouterLink to="/">{{item.name}}</RouterLink>-->
<!--        </li>-->
<!--      </ul>-->
<!--      <ul class="app-header-nav">-->
<!--        <li class="home">-->
<!--          <RouterLink to="/">首页</RouterLink>-->
<!--        </li>-->
<!--        <li v-for="item in categoryList" :key="item.id">-->
<!--          <RouterLink active-class="active" :to="`/category/${item.id}`">{{item.name}}</RouterLink>-->
<!--        </li>-->
<!--      </ul>-->
      <div class="search">
        <i class="iconfont icon-search"></i>
        <input type="text" placeholder="搜一搜">
      </div>
      <!-- 头部购物车 -->
      <HeaderCart/>
    </div>
  </header>
</template>


<style scoped lang='scss'>

.app-header {
  background: #fff;

  .container {
    display: flex;
    align-items: center;
  }

  .logo {
    width: 200px;
//控制头部最左边那个图片
    a {
      display: block;
      height: 120px;
      width: 100%;
      text-indent: -9999px;
      background: url('/assets/images/icon/shop.png') no-repeat center 40px / contain;
      background-size: 200px auto;
    }
  }

  .app-header-nav {
    width: 820px;
    display: flex;
    padding-left: 40px;
    position: relative;
    z-index: 998;

    li {
      margin-right: 40px;
      width: 38px;
      text-align: center;

      a {
        font-size: 16px;
        line-height: 32px;
        height: 32px;
        display: inline-block;

        &:hover {
          color: $xtxColor;
          border-bottom: 1px solid $xtxColor;
        }
      }

      .active {
        color: $xtxColor;
        border-bottom: 1px solid $xtxColor;
      }
    }
  }

  .search {
    width: 170px;
    height: 32px;
    position: relative;
    border-bottom: 1px solid #e7e7e7;
    line-height: 32px;

    .icon-search {
      font-size: 18px;
      margin-left: 5px;
    }

    input {
      width: 140px;
      padding-left: 5px;
      color: #666;
    }
  }

  .cart {
    width: 50px;

    .curr {
      height: 32px;
      line-height: 32px;
      text-align: center;
      position: relative;
      display: block;

      .icon-cart {
        font-size: 22px;
      }

      em {
        font-style: normal;
        position: absolute;
        right: 0;
        top: 0;
        padding: 1px 6px;
        line-height: 1;
        background: $helpColor;
        color: #fff;
        font-size: 12px;
        border-radius: 10px;
        font-family: Arial;
      }
    }
  }
}
</style>
