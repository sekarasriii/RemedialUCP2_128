package com.example.remed_pam_ucp2.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tableName: String,
    val action: String,
    val dataBefore: String?,
    val dataAfter: String?,
    val timestamp: Long = System.currentTimeMillis()
)
