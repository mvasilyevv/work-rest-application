package com.markvasilyevv.workrest

import com.markvasilyevv.workrest.config.DataInitialization
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkRestApplication

fun main(args: Array<String>) {
	val context = runApplication<WorkRestApplication>(*args)
	context.getBean(DataInitialization::class.java).init()
}
