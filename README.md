# 爬楼梯

# GameHandle 类方法列表
`GameHandle` 是一个 Java 类，属于 `cn.nostmc.slope` 包。以下是该类提供的一些方法及其简要说明。

## 方法列表

### simple(p: Player, amount: Double, radius: Double, title: String, subTitle: String)
- **功能**: 给予玩家箭和弓。
- **参数**:
  - `p` (Player): 玩家对象。
  - `amount` (Double): 数量。
  - `radius` (Double): 范围。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### giveArrowAndBow(p: Player, amount: Double, title: String, subTitle: String)
- **功能**: 给玩家箭矢。
- **参数**:
  - `p` (Player): 玩家对象。
  - `amount` (Double): 数量。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### giveArrow(p: Player, amount: Double, title: String, subTitle: String)
- **功能**: 批量推动矿车。
- **参数**:
  - `p` (Player): 玩家对象。
  - `amount` (Double): 数量。
  - `addLength` (Double): 增加的长度。
  - `mob` (String): 移动实体的名称。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### batchPushCart(p: Player, amount: Double, addLength: Double, mob: String, title: String, subTitle: String)
- **功能**: 使玩家失明。
- **参数**:
  - `p` (Player): 玩家对象。
  - `second` (Double): 持续时间（秒）。
  - `amplifier` (Double): 效果放大器。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### blind(p: Player, second: Double, amplifier: Double, title: String, subTitle: String)
- **功能**: 重生玩家。
- **参数**:
  - `p` (Player): 玩家对象。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### respawn(p: Player, title: String, subTitle: String)
- **功能**: 将玩家传送到结束点。
- **参数**:
  - `p` (Player): 玩家对象。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### teleportEnd(p: Player, title: String, subTitle: String)
- **功能**: 增加回合。
- **参数**:
  - `p` (Player): 玩家对象。
  - `amount` (Double): 数量。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### addTemplete(p: Player, amount: Double, title: String, subTitle: String)
- **功能**: 生成 TNT。
- **参数**:
  - `p` (Player): 玩家对象。
  - `amount` (Double): 数量。
  - `addHeight` (Double): 增加的高度。
  - `boomTicket` (Int): TNT 爆炸票。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### spawnTNT(p: Player, amount: Double, addHeight: Double, boomTicket: Int, title: String, subTitle: String)
- **功能**: 每秒击中玩家一下。
- **参数**:
  - `p` (Player): 玩家对象。
  - `second` (Double): 每秒。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### strikeLightning(p: Player, second: Double, title: String, subTitle: String)
- **功能**: 自定义 metadata。
- **参数**:
  - `p` (Player): 玩家对象。
  - `amount` (Double): 数量。
  - `addLength` (Double): 增加的长度。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。
  - `iaData` (String): 自定义数据。

### advanceBatchPushCart(p: Player, amount: Double, addLength: Double, title: String, subTitle: String, iaData: String)
- **功能**: 回血玩家。
- **参数**:
  - `p` (Player): 玩家对象。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### heal(p: Player, title: String, subTitle: String)
- **功能**: 增加条约药水效果。
- **参数**:
  - `p` (Player): 玩家对象。
  - `second` (Double): 持续时间（秒）。
  - `amplifier` (Int): 效果放大器。
  - `title` (String): 标题。
  - `subTitle` (String): 副标题。

### jumoANdSpeed(p: Player, second: Double, amplifier: Int, title: String, subTitle: String)
