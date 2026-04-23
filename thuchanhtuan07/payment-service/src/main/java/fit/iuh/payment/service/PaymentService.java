package fit.iuh.payment.service;

import fit.iuh.payment.messaging.BookingCreatedEvent;

public interface PaymentService {

   void processBookingCreated(BookingCreatedEvent event);
}