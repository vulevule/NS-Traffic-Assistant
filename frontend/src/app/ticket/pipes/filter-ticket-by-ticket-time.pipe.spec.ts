import { FilterTicketByTicketTimePipe } from './filter-ticket-by-ticket-time.pipe';

describe('FilterTicketByTicketTimePipe', () => {
  it('create an instance', () => {
    const pipe = new FilterTicketByTicketTimePipe();
    expect(pipe).toBeTruthy();
  });
});
