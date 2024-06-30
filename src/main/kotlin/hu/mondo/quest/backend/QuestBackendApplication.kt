package hu.mondo.quest.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.scheduling.annotation.EnableAsync
import javax.sql.DataSource


@SpringBootApplication
@ComponentScan(basePackages = ["hu.mondo.quest.backend"])
@EnableAsync
class QuestBackendApplication

fun main(args: Array<String>) {
	runApplication<QuestBackendApplication>(*args)
}
