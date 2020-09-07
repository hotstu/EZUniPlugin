 [![Download](https://api.bintray.com/packages/hglf/maven/EZUniPlugin/images/download.svg) ](https://bintray.com/hglf/maven/EZUniPlugin/_latestVersion)
 [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

EZUniPlugin
===================
uniApp Android插件开发脚手架

大幅简化UniApp Android插件开发， 官网上繁琐的配置、打包流程全部不需要！不需要！不需要！

实现模块自动发现，自动注册，功能增强。零配置！零配置！零配置！

只需要将aar包扔进文件就完了

* 使用方法（见examples）：

1. 在你的插件模块项目中引入
```gradle
    compileOnly 'github.hotstu.ezuniplugin:annotation:1.0.0'
    compileOnly 'github.hotstu.ezuniplugin:base:1.0.0'
    annotationProcessor 'github.hotstu.ezuniplugin:compiler:1.0.0'
```

2. 声明插件入口类，并继承ILoader，使用`@ModuleEntry`注解，并在init方法中注册你的module或者component 例如
```java
@ModuleEntry
public class TestStartup extends SimpleLoader {
    @Override
    public void init(Context context) {
        try {
            WXSDKEngine.registerModule("TestModule", TestModule.class, false);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
```

3. 将插件模块项目导出为aar包放入插件包的android文件夹中（ezpluginbox文件夹为模板）


4. 在hbuilder中选择插件包，云打包



*本项目由专业Android开发者开发并维护

## more about me

|简书| 掘金|JCenter | dockerHub|
| ------------- |------------- |------------- |------------- |
| [简书](https://www.jianshu.com/u/ca2207af2001) | [掘金](https://juejin.im/user/5bee320651882516be2ebbfe) |[JCenter ](https://bintray.com/hglf/maven)      | [dockerHub](https://hub.docker.com/u/hglf)|

