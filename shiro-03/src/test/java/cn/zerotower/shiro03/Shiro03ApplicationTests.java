package cn.zerotower.shiro03;

import cn.zerotower.shiro03.model.UserInfo;
import cn.zerotower.shiro03.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class Shiro03ApplicationTests {

    @Test
    void hashMD5Test(){
        UserInfo userInfo=new UserInfo();
        userInfo.setUid(1);
        userInfo.setUsername("zerotower");
        userInfo.setName("管理员");
        userInfo.setPassword("123456");
        String temp=userInfo.getUsername()+"zero";
        ByteSource salt=ByteSource.Util.bytes(temp);
        log.info(Md5Utils.computeMd5(userInfo.getUsername(),userInfo.getPassword()));
    }

}
