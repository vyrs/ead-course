package com.ead.course.consumers

import com.ead.course.dtos.UserEventDto
import com.ead.course.dtos.convertToUserModel
import com.ead.course.enums.ActionType
import com.ead.course.services.UserService
import com.netflix.appinfo.InstanceInfo
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UserConsumer(private val userService: UserService) {

    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(
                value = "\${ead.broker.queue.userEventQueue.name}",
                durable = "true"
            ),
            exchange = Exchange(
                value = "\${ead.broker.exchange.userEventExchange}",
                type = ExchangeTypes.FANOUT,
                ignoreDeclarationExceptions = "true"
            )
        )]
    )
    fun listenUserEvent(@Payload userEventDto: UserEventDto) {
        val userModel = userEventDto.convertToUserModel()
        when (ActionType.valueOf(userEventDto.actionType)) {
            ActionType.CREATE, ActionType.UPDATE -> userService.save(userModel)
            ActionType.DELETE -> userService.delete(userEventDto.userId)
        }
    }
}