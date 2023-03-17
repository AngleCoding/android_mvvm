package com.github.yuang.kt.mvm.entity


data class SearchEntForSamplingBean(
    val msg: String,
    val result: Result,
    val code: String
)

data class Result(
    val list: List<ListBean>,
    val pager: Pager
)

data class ListBean(
    val eUnicode: String,
    val eBusicode: Any,
    val eName: String,
    val eMptype: String,
    val eType: String,
    val eAddress: String,
    val eLegal: Any,
    val eLegalIdnum: Any,
    val eLegalIdEnd: Any,
    val eCaptial: Any,
    val eRegstatus: Any,
    val eEstablish: Any,
    val eValid: Any,
    val eAuditdate: Any,
    val eBusiness: Any,
    val eContact: String,
    val ePhone: String,
    val eProducts: String,
    val eScale: String,
    val eScaleUnit: String,
    val eWebsite: Any,
    val eEmail: Any,
    val eIndustry: String,
    val eRegorg: Any,
    val eArea: Any,
    val eAreaCode: Any,
    val eCredtime: Any,
    val certificate: String,
    val inspectedUnitName: Any,
    val payee: String,
    val bankName: String,
    val bankAccount: String,
    val handler: String,
    val handlerTel: Any,
    val certificateNo: String,
    val eStatus: String,
    val eCertification: String,
    val eCertidpros: Any,
    val eCertidcons: Any,
    val eLogo: Any,
    val isPerson: Any,
    val pbi: Any,
    val isNew: Any,
    val eMptypeName: Any,
    val remain: Any,
    val attachments: List<Attachment>,
    val pname: String,
    val pcode: String,
    val sname: String,
    val scode: String,
    val xname: String,
    val xcode: String,
    val pager: Any,
    val createdBy: String,
    val createdTime: String,
    val updatedBy: Any,
    val updatedTime: String,
    val status: String,
    val id: String
)

data class Pager(
    val pageNumber: Int,
    val pageSize: Int,
    val pageCount: Int,
    val recordCount: Int
)

data class Attachment(
    val fileType: String,
    val size: String,
    val category: Any,
    val bid: String,
    val urlPath: String,
    val eid: Any,
    val pictureSize: String,
    val status: String,
    val originalName: String,
    val remark: Any,
    val createdBy: String,
    val createdTime: String,
    val updatedBy: Any,
    val updatedTime: String,
    val id: String
)