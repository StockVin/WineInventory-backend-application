package com.wineinventory.orderoperationandmonitoring.domain.model.events;

import java.time.LocalDateTime;

/**
 * Evento de dominio que notifica al resto del sistema que una orden ha sido completada.
 * Puede ser utilizado para disparar integraciones externas o notificaciones al cliente.
 */
public record OrderCompletedEvent(Long orderId, String orderNumber, LocalDateTime completedAt) {
}
