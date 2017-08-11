BluetoothHandler（非低频蓝牙RFCOMM通信）
===================================
说明：用于连接设备蓝牙并发送或接收AT指令,其中已添加功能权限和蓝牙状态等广播接收，只需在回调接口中处理即可，此蓝牙连接只能一对一连接并传递数据，第一个上传项目，有问题的地方希望指出.


这里还有用于多连接的应用的蓝牙RFCOMM通信依赖 
-----------------------------------
[https://github.com/Yangandmore/BluetoothHandlerPlus](https://github.com/Yangandmore/BluetoothHandlerPlus)



在项目的build文件下添加依赖并Make
-----------------------------------
        allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io'} // 添加项
            }
        }


        dependencies {
            ...
            compile 'com.github.Yangandmore:BluetoothHandler:V2.1' // 添加项
        }


版本说明:
-----------------------------------
    目前版本：
    V2.1:增加 try-catch 功能
    旧版:
    V2.0:增加 try-catch 功能
    V1.0:基本功能



### 1.在Application中主功能初始化
        ```Java
        public class XXX extends Application {

            @Override
            public void onCreate() {
                super.onCreate();
                // 初始化需要this
                BluetoothInit.init(this);
            }
        }
        ```

### 2.在需要做蓝牙功能的地方做好蓝牙初始化及相应的蓝牙安全关闭

        ...

        private void init() {
               // 蓝牙功能初始化
               BluetoothInit.registerBroadcaseRecevier();
        }

        ...

        @Override
        protected void onDestroy() {
            super.onDestroy();
            // 蓝牙功能安全关闭
            BluetoothInit.unRegisterBroadcaseRecevier();
        }

### 3.可以在项目中添加监听器已监听蓝牙是否打开或关闭.
        BluetoothUtil.listenerBluetoothSwitch(new BluetoothSwitchCallBack() {
            @Override
            public void bluetoothSwitch(boolean flag) {
                if (flag) {
                    Toast.makeText(MainActivity.this, "蓝牙已打开", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });


### 4.可以得到手机已配对过的蓝牙对象列表。也可以在需要搜索的地方开启蓝牙搜索及蓝牙搜索关闭的地方添加相应功能,在搜索开启状态返回true的同时也会返回相应的蓝牙对象,其中包含他的名字和地址.当然也可以手动关闭蓝牙搜索功能.

        // 得到手机已配对过的蓝牙对象列表
        try {
            topList.addAll(BluetoothUtil.getBondedDevices());
        } catch (BluetoothSupportedException e) {
            e.printStackTrace();
        } catch (BluetoothSwitchCloseException e) {
            e.printStackTrace();
        }


        // 蓝牙的搜索及蓝牙搜索结束的状态回调
        try {
            BluetoothUtil.openSearchBluetooth(new BluetoothSearchCallBack() {
                @Override
                public void searchStateAndDate(boolean flag, BlueInfo blueInfo) {
                    if (flag) {
                        // 开始搜索并返回数据
                        bottomList.add(blueInfo);
                        bottomAdapter.notifyDataSetChanged();
                    } else {
                        // 搜索结束
                          ...
                    }
                }
            });
        } catch (BluetoothSwitchCloseException e) {
            e.printStackTrace();
        } catch (BluetoothSupportedException e) {
            e.printStackTrace();
        }


        // 手动关闭蓝牙搜索
        BluetoothUtil.closeSearchBluetooth();

### 5.开始对蓝牙的连接通信,返回的数据以原始16进制数据读取并传递,当然也可以手动断开蓝牙连接,一般情况下一定要及时关闭蓝牙连接.

        // 连接
        try {
            BluetoothUtil.connect(info, new BluetoothDateCallBack() {
                @Override
                public void bluetoothConnectCallBack(boolean flag) {
                    if (flag) {
                        // 连接成功
                    } else {
                        // 连接失败
                    }
                }
                @Override
                public void readBluetoothDate(byte[] bytes, boolean flag) {
                    if (flag) {
                        // 读取数据
                    } else {
                        // 连接断开
                    }
                }
            });
        } catch (BluetoothSupportedException e) {
            e.printStackTrace();
        } catch (BluetoothSwitchCloseException e) {
            e.printStackTrace();
        } catch (InputIncompleteException e) {
            e.printStackTrace();
        }

        // 写数据
        try{
            BluetoothUtil.write("...");
        } catch(IOException e) {
            e.printStackTrace();
        }

        // 手动断开蓝牙连接
        BluetoothUtil.close();
