package com.ead.course.configs

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface EadLog

fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)

inline fun <reified T : EadLog> T.log(): Logger = getLogger(T::class.java)