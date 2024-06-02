# 爬楼梯

# GameHandle 类方法列表
`GameHandle` 是一个 Java 类，属于 `cn.nostmc.slope` 包。以下是该类提供的一些方法及其简要说明。

## 方法列表

### simple(amount: Double, radius: Double)
- **功能**: 给予玩家箭和弓。
- **参数**:
  - `amount` (Double): 数量。
  - `radius` (Double): 范围。

### giveArrowAndBow(amount: Double)
- **功能**: 给玩家箭矢。
- **参数**:
  - `amount` (Double): 数量。

### giveArrow(amount: Double)
- **功能**: 给玩家箭。
- **参数**:
  - `amount` (Double): 数量。

### batchPushCart( amount: Double, addLength: Double, mob: String)
- **功能**: 批量推推车。
- **参数**:
  - `amount` : 数量
  - `addLength`  范围
  - `mob` 怪物Bukkit名字

### batchPushCart( amount: Double, addLength: Double)
- **功能**: 批量推推车怪物随机。
- **参数**:
  - `amount` : 数量
  - `addLength`  范围

### blind(second: Double, amplifier: Double)
- **功能**: 失明。
- **参数**:
  - `second` : 时间秒为单位
  - `amplifier`:  等级


### respawn()
- **功能**: 重生玩家。
- **参数**:

### teleportEnd()
- **功能**: 将玩家传送到结束点。
- **参数**:

### addTemplate(amount: Double)
- **功能**: 增加回合。
- **参数**:
  - `amount` (Double): 数量。

### spawnTNT(amount: Double, addHeight: Double, boomTicket: Int)
- **功能**: 生成 TNT炸飞。
- **参数**:
  - `amount` (Double): 数量。
  - `addHeight` (Double): 增加的高度。
  - `boomTicket` (Int): TNT 爆炸票。

### strikeLightning( second: Double)
- **功能**: 每秒击中玩家一下。
- **参数**:
  - `second` (Double): 每秒。

### advanceBatchPushCart( amount: Double, addLength: Double, iaData: String)
- **功能**: 自定义 metadata。
- **参数**:
  - `amount` (Double): 数量。
  - `addLength` (Double): 增加的长度。
  - `iaData` (String): 自定义数据。

### heal()
- **功能**: 回血玩家。
- **参数**:

### jumpAndSpeed(second: Double, amplifier: Int)
- **功能**: 增加条约药水效果。
- **参数**:
  - `second` (Double): 持续时间（秒）。
  - `amplifier` (Int): 效果放大器。