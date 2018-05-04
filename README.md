# CScrollMenu
开源控件——导航菜单

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


