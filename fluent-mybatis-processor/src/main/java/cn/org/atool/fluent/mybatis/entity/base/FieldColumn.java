package cn.org.atool.fluent.mybatis.entity.base;

import cn.org.atool.fluent.mybatis.utility.MybatisUtil;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

@Data
@Accessors(chain = true)
public class FieldColumn {
    /**
     * 是否主键
     */
    private boolean primary = false;
    /**
     * 自增
     */
    private boolean autoIncrease = false;

    @Setter(AccessLevel.NONE)
    private String property;

    private String column;

    private Type type;

    private JdbcType jdbcType;

    private String seqName;

    private String numericScale;

    private String typeHandler;

    private boolean notLarge = true;

    private String insert;

    private String update;

    public FieldColumn setProperty(String property) {
        this.property = property;
        if (column == null) {
            column = MybatisUtil.camelToUnderline(this.property, false);
        }
        return this;
    }

    public boolean isPrimitive() {
        return type.isPrimitive();
    }

    /**
     * get方法名称
     *
     * @return
     */
    public String getMethodName() {
        if (isPrimitive() && type.getTag() == TypeTag.BOOLEAN) {
            return "is" + MybatisUtil.capitalFirst(this.property, "is");
        } else {
            return "get" + MybatisUtil.capitalFirst(this.property, null);
        }
    }

    /**
     * set方法名称
     *
     * @return
     */
    public String setMethodName() {
        if (isPrimitive() && type.getTag() == TypeTag.BOOLEAN) {
            return "set" + MybatisUtil.capitalFirst(this.property, "is");
        } else {
            return "set" + MybatisUtil.capitalFirst(this.property, null);
        }
    }
}