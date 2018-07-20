# CScrollMenu
开源控件——导航菜单

###### 简书: [https://www.jianshu.com/p/b9f0077bd237](https://www.jianshu.com/p/b9f0077bd237)

控件名称：XSlidingLayout
-------------------
- 可以设置是否关联viewpager
```
xsliding.setViewPager(vpager)
```
- 可以设置是否横向铺满显示
```
xsliding.isFillViewport = isFixation
```
- 可以设置菜单切换距离左边的距离
```
xsliding.setPositionOffset(100)
```
- 可以设置菜单是否加入图标（本地图片或者网络图片）
```
/**
 * 菜单数据model，使用的时候，如果是网络获取，可以继承此类进行相关处理
 *
 * @author 陈聪 2018-01-17 16:28
 */
class XMenuModel(var title: String = "",//菜单名称
                 var position: Int = 0,
                 var drawable_position:Int = XSlidingTabLayout.DRAWABLE_LEFT,//图片标签显示位置
                 var normal_icon: Int = 0,//正常情况本地图片标签
                 var selected_icon: Int = 0,//选中情况本地图片标签
                 var normal_net_icon: String = "",//正常情况网络图片标签
                 var selected_net_icon: String = "",//选中情况网络图片标签
                 var normalSize: Float = 15f,//正常情况菜单size
                 var selectedSize: Float = 0f,//选中情况菜单size
                 var normal_color: Int = 0,//正常情况菜单颜色
                 var selected_color: Int = 0) : Serializable//选中情况菜单颜色

XMenuModel里面设置，如果normal_icon和selected_icon为0或者normal_net_icon和selected_net_icon为""，表示不设置图片标签

mens.add(XMenuModel("家具", 0, picPosition, normal_icon, selected_icon, normal_net_pic, selected_net_pic, 15f, 15f, R.color.black_66, R.color.red_ff5303))

xsliding.setMenus(mens, object : XSlidingTabLayout.OnClickPositionBack {
                    override fun onClickBack(view: View, position: Int) {
                        Toast.makeText(this@ShowActivity, mens[position].title, Toast.LENGTH_SHORT).show()
                    }
                })
```
- 可以设置菜单图片显示位置（上面，下面，左面，右面）
```
xsliding.setInnerGravity(Gravity.LEFT)
```
相关效果如下：
![SVID_20180720_104245.gif](https://upload-images.jianshu.io/upload_images/6206982-dd69a205c9ec0c49.gif?imageMogr2/auto-orient/strip)
![SVID_20180720_104322.gif](https://upload-images.jianshu.io/upload_images/6206982-2bfb4dc058f40990.gif?imageMogr2/auto-orient/strip)

![SVID_20180720_104352.gif](https://upload-images.jianshu.io/upload_images/6206982-8ab3059db8faf1a3.gif?imageMogr2/auto-orient/strip)

![SVID_20180720_104411.gif](https://upload-images.jianshu.io/upload_images/6206982-79e15ec6f44538ff.gif?imageMogr2/auto-orient/strip)

![SVID_20180720_104436.gif](https://upload-images.jianshu.io/upload_images/6206982-91b1fbad9b26142f.gif?imageMogr2/auto-orient/strip)

![SVID_20180720_104705.gif](https://upload-images.jianshu.io/upload_images/6206982-64d63b005593beaf.gif?imageMogr2/auto-orient/strip)

![SVID_20180720_104730.gif](https://upload-images.jianshu.io/upload_images/6206982-993a0289b6c6f295.gif?imageMogr2/auto-orient/strip)



