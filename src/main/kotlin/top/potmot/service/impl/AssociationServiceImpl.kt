package top.potmot.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.model.dto.GenAssociationCommonInput
import top.potmot.model.dto.GenAssociationCommonView
import top.potmot.model.dto.GenAssociationPreviewView
import top.potmot.model.query.AssociationQuery
import top.potmot.service.AssociationService
import java.util.*

@Service
class AssociationServiceImpl(

): AssociationService {
    override fun selectAssociations(tableIds: List<Long>): List<GenAssociationPreviewView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun saveAssociations(associations: List<GenAssociationCommonInput>): List<GenAssociationCommonView> {
        TODO("Not yet implemented")
    }

    override fun queryAssociations(query: AssociationQuery): List<GenAssociationCommonView> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteAssociations(ids: List<Long>): Int {
        TODO("Not yet implemented")
    }
}
