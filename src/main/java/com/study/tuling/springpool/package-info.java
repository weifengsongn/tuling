/**
 * 手写spring的bean生成。
 *
 * 流程设计：
 * 1. 根据用途去反推设计；
 * 2. 先设计出基础case然后在去做复杂场景的完善；
 * 3. 当前想实现功能列表：
 *    （1）自己先创建注解玩一下，然后用字节码文件操作一把；
 *    （2）单例 多例支持；
 *    （3）类扫描支持；
 *    （5）从文件系统中读取字节码文件
 *    （4）beanDefinesion的文件生成；
 *
 * @author wfsong
 * @date 2022/11/8 18:06
 */
package com.study.tuling.springpool;