note：
1、方法未在flink任务中测试过，只是在本地跑过单元测试
2、JsonUtils存在多层泛型的bug，生产使用时建议替换，通常情况下没有问题

todo：
1、目前是根据类名截取作为schema的名字，所以存在多个同名类混淆的问题
2、注解值枚举化：目前对protobuf的语法不熟悉，并未给出枚举值
3、AbstractDeserializationSchema 不接受泛型的问题