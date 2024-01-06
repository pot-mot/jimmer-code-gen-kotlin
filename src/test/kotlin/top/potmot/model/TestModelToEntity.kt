package top.potmot.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenModelInput
import top.potmot.service.ModelService
import top.potmot.service.PreviewService
import top.potmot.util.replaceSinceTimeComment

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToEntity (
    @Autowired val modelService: ModelService,
    @Autowired val previewService: PreviewService
) {
    @Test
    @Order(1)
    fun testCreateJavaEntity() {
        modelInput.language = GenLanguage.JAVA

        val id = modelService.save(modelInput)

        val entityCodes = previewService.previewModelEntity(id, true)

        assertEquals(
            javaResult.trim(),
            entityCodes.toString().trim().replaceSinceTimeComment()
        )
    }

    @Test
    @Order(2)
    fun testCreateKotlinEntity() {
        modelInput.language = GenLanguage.KOTLIN

        val id = modelService.save(modelInput)

        val entityCodes = previewService.previewModelEntity(id, true)

        assertEquals(
            kotlinResult.trim(),
            entityCodes.toString().trim().replaceSinceTimeComment()
        )
    }

    private val modelInput = GenModelInput(
        name = "test",
        remark = "test",
        language = GenLanguage.JAVA,
        dataSourceType = DataSourceType.MySQL,
        packagePath = "top.potmot.model",
        syncConvertEntity = false,
        graphData = "{\"json\":{\"cells\":[{\"position\":{\"x\":1130,\"y\":1140},\"size\":{\"width\":148,\"height\":64},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"49330bed-d6e9-4195-8f4f-1dd1b93e6a01\",\"data\":{\"table\":{\"name\":\"m_n_target\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"ID\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":392,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"231caa84-77e7-48d8-bf3f-a6156741b734\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":148}}}]}},{\"position\":{\"x\":1100,\"y\":500},\"size\":{\"width\":124,\"height\":64},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"01e1cd22-370d-43a3-accc-cad19c67171a\",\"data\":{\"table\":{\"name\":\"o_o_target\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":420,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"3a92939a-1b9b-4530-a894-e690882eff44\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":124}}}]}},{\"position\":{\"x\":790,\"y\":500},\"size\":{\"width\":156,\"height\":94},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"4fa097d7-513a-4539-92cb-3cdcdc94b8fb\",\"data\":{\"table\":{\"name\":\"o_o_source\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false},{\"orderKey\":2,\"name\":\"target_id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":false,\"autoIncrement\":false,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":474,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"a7ff2a53-1341-47e2-ae1b-cd7bc033e060\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":156}}},{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"4e6f6321-653f-4e8c-b0c5-0c78c09b6176\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":156}}}]}},{\"position\":{\"x\":790,\"y\":890},\"size\":{\"width\":135,\"height\":64},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"6501e307-dd37-4609-9d9a-3ab91533d796\",\"data\":{\"table\":{\"name\":\"o_m_source\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":500,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"309b3af2-9433-4115-b2d0-3907397c46d4\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":135}}}]}},{\"shape\":\"ASSOCIATION_EDGE\",\"router\":{\"name\":\"er\",\"args\":{\"direction\":\"H\"}},\"id\":\"173e158f-87b5-43d2-94f8-1e88d3a0c038\",\"data\":{\"association\":{\"associationType\":\"ONE_TO_ONE\",\"fake\":false,\"name\":\"fk_o_o_source_target_id_o_o_target_id\",\"sourceTable\":{\"name\":\"o_o_source\",\"comment\":\"\"},\"targetTable\":{\"name\":\"o_o_target\",\"comment\":\"\"},\"columnReferences\":[{\"sourceColumn\":{\"name\":\"target_id\",\"comment\":\"\",\"type\":\"BIGINT\",\"typeCode\":-5},\"targetColumn\":{\"name\":\"id\",\"comment\":\"\",\"type\":\"BIGINT\",\"typeCode\":-5}}]}},\"zIndex\":518,\"labels\":[{\"markup\":[{\"tagName\":\"rect\",\"selector\":\"body\"},{\"tagName\":\"text\",\"selector\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\"}],\"attrs\":{\"ASSOCIATION_LABEL_TEXT_SELECTOR\":{\"text\":\"ONE_TO_ONE\",\"fill\":\"var(--common-color)\",\"fontWeight\":\"normal\"},\"body\":{\"ref\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\",\"fill\":\"#f5f5f5\"}}}],\"source\":{\"cell\":\"4fa097d7-513a-4539-92cb-3cdcdc94b8fb\",\"port\":\"4e6f6321-653f-4e8c-b0c5-0c78c09b6176\"},\"target\":{\"cell\":\"01e1cd22-370d-43a3-accc-cad19c67171a\",\"port\":\"3a92939a-1b9b-4530-a894-e690882eff44\"}},{\"position\":{\"x\":790,\"y\":660},\"size\":{\"width\":128,\"height\":64},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"704ba871-8875-490a-88d3-05ae19af7fe5\",\"data\":{\"table\":{\"name\":\"m_o_target\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":527,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"b1a3ce5a-1296-4a84-9c9c-220131063f03\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":128}}}]}},{\"position\":{\"x\":500,\"y\":690},\"size\":{\"width\":159,\"height\":94},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"3033e497-f601-4b1f-92b7-031ecee23d55\",\"data\":{\"table\":{\"name\":\"tree_node\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false},{\"orderKey\":2,\"name\":\"parent_id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":false,\"autoIncrement\":false,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":529,\"children\":[\"7edbe4e0-1b97-4512-930b-896ac305b8f1\"],\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"85a42e05-b520-43ac-986d-bef2f4d2f17b\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":159}}},{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"aa1c0e17-f1f9-44b5-95ec-b2cd116835ac\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":159}}}]}},{\"shape\":\"ASSOCIATION_EDGE\",\"router\":{\"name\":\"orth\",\"args\":{\"direction\":\"H\",\"padding\":20}},\"id\":\"7edbe4e0-1b97-4512-930b-896ac305b8f1\",\"data\":{\"association\":{\"associationType\":\"MANY_TO_ONE\",\"fake\":false,\"name\":\"fk_tree_node_parent_id_tree_node_id\",\"sourceTable\":{\"name\":\"tree_node\",\"comment\":\"\"},\"targetTable\":{\"name\":\"tree_node\",\"comment\":\"\"},\"columnReferences\":[{\"sourceColumn\":{\"name\":\"parent_id\",\"comment\":\"\",\"type\":\"BIGINT\",\"typeCode\":-5},\"targetColumn\":{\"name\":\"id\",\"comment\":\"\",\"type\":\"BIGINT\",\"typeCode\":-5}}]}},\"zIndex\":530,\"parent\":\"3033e497-f601-4b1f-92b7-031ecee23d55\",\"labels\":[{\"markup\":[{\"tagName\":\"rect\",\"selector\":\"body\"},{\"tagName\":\"text\",\"selector\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\"}],\"attrs\":{\"ASSOCIATION_LABEL_TEXT_SELECTOR\":{\"text\":\"MANY_TO_ONE\",\"fill\":\"var(--common-color)\",\"fontWeight\":\"normal\"},\"body\":{\"ref\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\",\"fill\":\"#f5f5f5\"}}}],\"source\":{\"cell\":\"3033e497-f601-4b1f-92b7-031ecee23d55\",\"port\":\"aa1c0e17-f1f9-44b5-95ec-b2cd116835ac\"},\"target\":{\"cell\":\"3033e497-f601-4b1f-92b7-031ecee23d55\",\"port\":\"85a42e05-b520-43ac-986d-bef2f4d2f17b\"}},{\"position\":{\"x\":1110,\"y\":670},\"size\":{\"width\":162,\"height\":94},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"10b1b3cb-e5d8-4ff7-9b5b-933b9388f87a\",\"data\":{\"table\":{\"name\":\"m_o_source\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false},{\"orderKey\":2,\"name\":\"source_id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":false,\"autoIncrement\":false,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":538,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"d34f52a6-48af-4565-95ea-60d320b0c87b\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":162}}},{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"5c1d33d8-e4ff-4947-9da9-ba3f5b30f190\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":162}}}]}},{\"shape\":\"ASSOCIATION_EDGE\",\"router\":{\"name\":\"er\",\"args\":{\"direction\":\"H\"}},\"id\":\"db16a30d-bf8e-4123-9bc7-9d7277405238\",\"data\":{\"association\":{\"associationType\":\"MANY_TO_ONE\",\"fake\":false,\"name\":\"fk_m_o_source_source_id_m_o_target_id\",\"sourceTable\":{\"name\":\"m_o_source\",\"comment\":\"\"},\"targetTable\":{\"name\":\"m_o_target\",\"comment\":\"\"},\"columnReferences\":[{\"sourceColumn\":{\"name\":\"source_id\",\"comment\":\"\",\"type\":\"BIGINT\",\"typeCode\":-5},\"targetColumn\":{\"name\":\"id\",\"comment\":\"\",\"type\":\"BIGINT\",\"typeCode\":-5}}]}},\"zIndex\":539,\"labels\":[{\"markup\":[{\"tagName\":\"rect\",\"selector\":\"body\"},{\"tagName\":\"text\",\"selector\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\"}],\"attrs\":{\"ASSOCIATION_LABEL_TEXT_SELECTOR\":{\"text\":\"MANY_TO_ONE\",\"fill\":\"var(--common-color)\",\"fontWeight\":\"normal\"},\"body\":{\"ref\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\",\"fill\":\"#f5f5f5\"}}}],\"source\":{\"cell\":\"10b1b3cb-e5d8-4ff7-9b5b-933b9388f87a\",\"port\":\"5c1d33d8-e4ff-4947-9da9-ba3f5b30f190\"},\"target\":{\"cell\":\"704ba871-8875-490a-88d3-05ae19af7fe5\",\"port\":\"b1a3ce5a-1296-4a84-9c9c-220131063f03\"}},{\"position\":{\"x\":810,\"y\":1130},\"size\":{\"width\":148,\"height\":64},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"4325dcc5-3259-46e3-b0db-0653a317157f\",\"data\":{\"table\":{\"name\":\"m_n_source\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"ID\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":540,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"b4b0309b-a46f-417b-bea0-d0831b6aefe2\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":148}}}]}},{\"shape\":\"ASSOCIATION_EDGE\",\"router\":{\"name\":\"er\",\"args\":{\"direction\":\"H\"}},\"id\":\"49be954e-cdfe-4472-b84a-5a8ebe2cf673\",\"data\":{\"association\":{\"associationType\":\"MANY_TO_MANY\",\"fake\":false,\"name\":\"many_to_many_source__to__many_to_many_target__mapping_table__for_long_table_name_test\",\"sourceTable\":{\"name\":\"m_n_source\",\"comment\":\"\"},\"targetTable\":{\"name\":\"m_n_target\",\"comment\":\"\"},\"columnReferences\":[{\"sourceColumn\":{\"name\":\"id\",\"comment\":\"ID\",\"type\":\"BIGINT\",\"typeCode\":-5},\"targetColumn\":{\"name\":\"id\",\"comment\":\"ID\",\"type\":\"BIGINT\",\"typeCode\":-5}}]}},\"zIndex\":541,\"labels\":[{\"markup\":[{\"tagName\":\"rect\",\"selector\":\"body\"},{\"tagName\":\"text\",\"selector\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\"}],\"attrs\":{\"ASSOCIATION_LABEL_TEXT_SELECTOR\":{\"text\":\"MANY_TO_MANY\",\"fill\":\"var(--common-color)\",\"fontWeight\":\"normal\"},\"body\":{\"ref\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\",\"fill\":\"#f5f5f5\"}}}],\"source\":{\"cell\":\"4325dcc5-3259-46e3-b0db-0653a317157f\",\"port\":\"b4b0309b-a46f-417b-bea0-d0831b6aefe2\"},\"target\":{\"cell\":\"49330bed-d6e9-4195-8f4f-1dd1b93e6a01\",\"port\":\"231caa84-77e7-48d8-bf3f-a6156741b734\"}},{\"position\":{\"x\":1120,\"y\":920},\"size\":{\"width\":162,\"height\":94},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"7f1589cd-b8e5-4c65-8951-de75882e1ddc\",\"data\":{\"table\":{\"name\":\"o_m_target\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false},{\"orderKey\":2,\"name\":\"source_id\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":false,\"autoIncrement\":false,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[]}},\"zIndex\":542,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"2534ef6e-6dae-4673-a56d-c022bb93c57f\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":162}}},{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"1b51ec31-ae33-4e73-b02c-fa97ca25e243\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":162}}}]}},{\"shape\":\"ASSOCIATION_EDGE\",\"router\":{\"name\":\"er\",\"args\":{\"direction\":\"H\"}},\"id\":\"f5301f45-2eeb-4e17-805e-88604cdfc398\",\"data\":{\"association\":{\"associationType\":\"ONE_TO_MANY\",\"fake\":false,\"name\":\"fk_o_m_source_id_o_m_target_source_id\",\"sourceTable\":{\"name\":\"o_m_source\",\"comment\":\"\"},\"targetTable\":{\"name\":\"o_m_target\",\"comment\":\"\"},\"columnReferences\":[{\"sourceColumn\":{\"name\":\"id\",\"comment\":\"\",\"type\":\"BIGINT\",\"typeCode\":-5},\"targetColumn\":{\"name\":\"source_id\",\"comment\":\"\",\"type\":\"BIGINT\",\"typeCode\":-5}}]}},\"zIndex\":543,\"labels\":[{\"markup\":[{\"tagName\":\"rect\",\"selector\":\"body\"},{\"tagName\":\"text\",\"selector\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\"}],\"attrs\":{\"ASSOCIATION_LABEL_TEXT_SELECTOR\":{\"text\":\"ONE_TO_MANY\",\"fill\":\"var(--common-color)\",\"fontWeight\":\"normal\"},\"body\":{\"ref\":\"ASSOCIATION_LABEL_TEXT_SELECTOR\",\"fill\":\"#f5f5f5\"}}}],\"source\":{\"cell\":\"6501e307-dd37-4609-9d9a-3ab91533d796\",\"port\":\"309b3af2-9433-4115-b2d0-3907397c46d4\"},\"target\":{\"cell\":\"7f1589cd-b8e5-4c65-8951-de75882e1ddc\",\"port\":\"1b51ec31-ae33-4e73-b02c-fa97ca25e243\"}}]},\"zoom\":0.85,\"transform\":\"matrix(0.85,0,0,0.85,-281.49998088444033,-429.2999636201308)\"}"
    )

    private val javaResult = """
[(top/potmot/model/MNTarget.java, package top.potmot.model;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_N_TARGET")
public interface MNTarget {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @ManyToMany(mappedBy = "mNTargets")
    List<MNSource> mNSources();
    
    /**
     * ID
     */
    @IdView("mNSources")
    List<long> mNSourceIds();
}
), (top/potmot/model/OOTarget.java, package top.potmot.model;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

/**
 * @author 
 */
@Entity
@Table(name = "O_O_TARGET")
public interface OOTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToOne(mappedBy = "oOTarget")
    @Nullable
    OOSource oOSource();
    
    @IdView("oOSource")
    @Nullable
    Long oOSourceId();
}
), (top/potmot/model/OOSource.java, package top.potmot.model;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "O_O_SOURCE")
public interface OOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToOne
    @JoinColumn(
        name = "TARGET_ID",
        referencedColumnName = "ID"
    )
    OOTarget oOTarget();
    
    @IdView("oOTarget")
    long oOTargetId();
}
), (top/potmot/model/OMSource.java, package top.potmot.model;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "O_M_SOURCE")
public interface OMSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToMany(mappedBy = "oMSource")
    List<OMTarget> oMTargets();
    
    @IdView("oMTargets")
    List<long> oMTargetIds();
}
), (top/potmot/model/MOTarget.java, package top.potmot.model;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_O_TARGET")
public interface MOTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToMany(mappedBy = "mOTarget")
    List<MOSource> mOSources();
    
    @IdView("mOSources")
    List<long> mOSourceIds();
}
), (top/potmot/model/TreeNode.java, package top.potmot.model;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "TREE_NODE")
public interface TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToMany(mappedBy = "treeNode")
    List<TreeNode> treeNodes();
    
    @IdView("treeNodes")
    List<long> treeNodeIds();
    
    @ManyToOne
    @JoinColumn(
        name = "PARENT_ID",
        referencedColumnName = "ID"
    )
    TreeNode treeNode();
    
    @IdView("treeNode")
    long treeNodeId();
}
), (top/potmot/model/MOSource.java, package top.potmot.model;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_O_SOURCE")
public interface MOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @ManyToOne
    @JoinColumn(
        name = "SOURCE_ID",
        referencedColumnName = "ID"
    )
    MOTarget mOTarget();
    
    @IdView("mOTarget")
    long mOTargetId();
}
), (top/potmot/model/MNSource.java, package top.potmot.model;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinTable;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_N_SOURCE")
public interface MNSource {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @ManyToMany
    @JoinTable(
        name = "MANY_TO_MANY_SOURCE__TO__MANY_TO_MANY_TARGET__MAPPING__319B0383",
        joinColumnName = "M_N_SOURCE_ID",
        inverseJoinColumnName = "M_N_TARGET_ID"
    )
    List<MNTarget> mNTargets();
    
    /**
     * ID
     */
    @IdView("mNTargets")
    List<long> mNTargetIds();
}
), (top/potmot/model/OMTarget.java, package top.potmot.model;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "O_M_TARGET")
public interface OMTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @ManyToOne
    @JoinColumn(
        name = "ID",
        referencedColumnName = "SOURCE_ID"
    )
    OMSource oMSource();
    
    @IdView("oMSource")
    long oMSourceId();
}
)]
    """

    private val kotlinResult ="""
[(top/potmot/model/MNTarget.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_N_TARGET")
interface MNTarget {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @ManyToMany(mappedBy = "mNTargets")
    val mNSources: List<MNSource>
    
    /**
     * ID
     */
    @IdView("mNSources")
    val mNSourceIds: List<Long>
}
), (top/potmot/model/OOTarget.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "O_O_TARGET")
interface OOTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToOne(mappedBy = "oOTarget")
    val oOSource: OOSource?
    
    @IdView("oOSource")
    val oOSourceId: Long?
}
), (top/potmot/model/OOSource.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "O_O_SOURCE")
interface OOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToOne
    @JoinColumn(
        name = "TARGET_ID",
        referencedColumnName = "ID"
    )
    val oOTarget: OOTarget
    
    @IdView("oOTarget")
    val oOTargetId: Long
}
), (top/potmot/model/OMSource.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "O_M_SOURCE")
interface OMSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToMany(mappedBy = "oMSource")
    val oMTargets: List<OMTarget>
    
    @IdView("oMTargets")
    val oMTargetIds: List<Long>
}
), (top/potmot/model/MOTarget.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_O_TARGET")
interface MOTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToMany(mappedBy = "mOTarget")
    val mOSources: List<MOSource>
    
    @IdView("mOSources")
    val mOSourceIds: List<Long>
}
), (top/potmot/model/TreeNode.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "TREE_NODE")
interface TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToMany(mappedBy = "treeNode")
    val treeNodes: List<TreeNode>
    
    @IdView("treeNodes")
    val treeNodeIds: List<Long>
    
    @ManyToOne
    @JoinColumn(
        name = "PARENT_ID",
        referencedColumnName = "ID"
    )
    val treeNode: TreeNode
    
    @IdView("treeNode")
    val treeNodeId: Long
}
), (top/potmot/model/MOSource.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_O_SOURCE")
interface MOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @ManyToOne
    @JoinColumn(
        name = "SOURCE_ID",
        referencedColumnName = "ID"
    )
    val mOTarget: MOTarget
    
    @IdView("mOTarget")
    val mOTargetId: Long
}
), (top/potmot/model/MNSource.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_N_SOURCE")
interface MNSource {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @ManyToMany
    @JoinTable(
        name = "MANY_TO_MANY_SOURCE__TO__MANY_TO_MANY_TARGET__MAPPING__319B0383",
        joinColumnName = "M_N_SOURCE_ID",
        inverseJoinColumnName = "M_N_TARGET_ID"
    )
    val mNTargets: List<MNTarget>
    
    /**
     * ID
     */
    @IdView("mNTargets")
    val mNTargetIds: List<Long>
}
), (top/potmot/model/OMTarget.kt, package top.potmot.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "O_M_TARGET")
interface OMTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @ManyToOne
    @JoinColumn(
        name = "ID",
        referencedColumnName = "SOURCE_ID"
    )
    val oMSource: OMSource
    
    @IdView("oMSource")
    val oMSourceId: Long
}
)]
    """
}
