package hu.mondo.quest.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@ComponentScan(basePackages = ["hu.mondo.quest.backend"])
@EnableAsync
class QuestBackendApplication

fun main(args: Array<String>) {
	runApplication<QuestBackendApplication>(*args)
}
