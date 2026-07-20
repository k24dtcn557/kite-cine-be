CREATE UNIQUE INDEX IF NOT EXISTS idx_tkt_active_ticket
    ON ticket (showtime_id, seat_id)
    WHERE status IN ('HOLD', 'CONFIRMED');