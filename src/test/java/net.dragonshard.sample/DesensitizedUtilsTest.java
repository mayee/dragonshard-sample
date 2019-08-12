package net.dragonshard.sample;

import net.dragonshard.dsf.core.toolkit.DesensitizedUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 脱敏功能测试
 *
 * @author mayee
 * @version v1.0
 **/
@SuppressWarnings("all")
public class DesensitizedUtilsTest {

    @Test(description = "前后保留功能")
    public void prefixAndSuffixLength() {
        Assert.assertNull(DesensitizedUtils.desValue(null, 1, 1, "*"), null);
        Assert.assertEquals(DesensitizedUtils.desValue("abc", 1, 1, "*"), "a*c");
        Assert.assertEquals(DesensitizedUtils.desValue("abc", 0, 1, "*"), "**c");
        Assert.assertEquals(DesensitizedUtils.desValue("abc", 1, 0, "*"), "a**");
        Assert.assertEquals(DesensitizedUtils.desValue("abcd", 2, 1, "*"), "ab*d");
        Assert.assertEquals(DesensitizedUtils.desValue("abcd", 1, 2, "*"), "a*cd");
        Assert.assertEquals(DesensitizedUtils.desValue("abc", 2, 2, "*"), "abc");
    }

    @Test(description = "chineseName")
    public void chineseName() {
        Assert.assertNull(DesensitizedUtils.chineseName(null), null);
        Assert.assertEquals(DesensitizedUtils.chineseName("龙晶脚手架"), "****架");
        Assert.assertEquals(DesensitizedUtils.chineseName("龙晶"), "*晶");
    }

    @Test(description = "idCardNum")
    public void idCardNum() {
        Assert.assertNull(DesensitizedUtils.idCardNum(null), null);
        Assert.assertEquals(DesensitizedUtils.idCardNum("340304200001010839"), "340304********0839");
        Assert.assertEquals(DesensitizedUtils.idCardNum("34030420000101083X"), "340304********083X");
    }

    @Test(description = "email")
    public void email() {
        Assert.assertNull(DesensitizedUtils.email(null), null);
        Assert.assertEquals(DesensitizedUtils.email("saber@dragonshard.net"), "s****@dragonshard.net");
    }

    @Test(description = "fixedPhone")
    public void fixedPhone() {
        Assert.assertNull(DesensitizedUtils.fixedPhone(null), null);
        Assert.assertEquals(DesensitizedUtils.fixedPhone("62580000"), "****0000");
    }

    @Test(description = "mobilePhone")
    public void mobilePhone() {
        Assert.assertNull(DesensitizedUtils.mobilePhone(null), null);
        Assert.assertEquals(DesensitizedUtils.mobilePhone("18616354707"), "186****4707");
    }

    @Test(description = "bankCard")
    public void bankCard() {
        Assert.assertNull(DesensitizedUtils.bankCard(null), null);
        Assert.assertEquals(DesensitizedUtils.bankCard("6222024509000654123"), "622202*********4123");
    }

    @Test(description = "address")
    public void address() {
        Assert.assertNull(DesensitizedUtils.address(null), null);
        Assert.assertEquals(DesensitizedUtils.address("中建财富中心18层"), "中建财富中心***");
    }

    @Test(description = "password")
    public void password() {
        Assert.assertNull(DesensitizedUtils.password(null), null);
        Assert.assertEquals(DesensitizedUtils.password("123"), "******");
        Assert.assertEquals(DesensitizedUtils.password("1234567890"), "******");
    }

    @Test(description = "key")
    public void key() {
        Assert.assertNull(DesensitizedUtils.key(null), null);
        Assert.assertEquals(DesensitizedUtils.key("123"), "***123");
        Assert.assertEquals(DesensitizedUtils.key("1234"), "***234");
        Assert.assertEquals(DesensitizedUtils.key("1234567890"), "***890");
    }

}
