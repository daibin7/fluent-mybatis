package cn.org.atool.fluent.mybatis.method;

import cn.org.atool.fluent.mybatis.generate.DM;
import cn.org.atool.fluent.mybatis.generate.ITable;
import cn.org.atool.fluent.mybatis.generate.entity.mapper.NoPrimaryMapper;
import cn.org.atool.fluent.mybatis.generate.entity.mapper.UserMapper;
import cn.org.atool.fluent.mybatis.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.test4j.hamcrest.matcher.string.StringMode;

public class DeleteByIdTest extends BaseTest {
    @Autowired
    private UserMapper mapper;

    @Autowired
    private NoPrimaryMapper noPrimaryMapper;

    @Test
    public void testDeleteById() {
        db.table(ITable.t_user).clean().insert(DM.user.initTable(2)
            .id.values(23L, 24L)
            .userName.values("user1", "user2")
        );
        mapper.deleteById(24);
        db.sqlList().wantFirstSql()
            .eq("DELETE FROM t_user WHERE id = ?", StringMode.SameAsSpace);
        db.table(ITable.t_user).query().eqDataMap(DM.user.table(1)
            .id.values(23L)
            .userName.values("user1")
        );
    }

    @Test
    public void test_selectById_noPrimary() throws Exception {
        db.table(t_no_primary).clean().insert(DM.noPrimary.initTable(3)
            .column1.values(1, 2, 3)
            .column2.values("c1", "c2", "c3")
        );
        want.exception(() -> noPrimaryMapper.deleteById(3L), MyBatisSystemException.class);
    }
}
