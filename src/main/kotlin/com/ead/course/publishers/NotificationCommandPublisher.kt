package com.ead.course.publishers

import com.ead.course.dtos.NotificationCommandDto
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class NotificationCommandPublisher(private val rabbitTemplate: RabbitTemplate) {

    @Value(value = "\${ead.broker.exchange.notificationCommandExchange}")
    private val notificationCommandExchange: String? = null

    @Value(value = "\${ead.broker.key.notificationCommandKey}")
    private val notificationCommandKey: String? = null

    fun publishNotificationCommand(notificationCommandDto: NotificationCommandDto) {
        rabbitTemplate.convertAndSend(
            notificationCommandExchange!!,
            notificationCommandKey!!, notificationCommandDto
        )
    }
}