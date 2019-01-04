package com.team9.NSTrafficAssistant;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.team9.NSTrafficAssistant.repository.PricelistItemRepositoryIntegrationTest;
import com.team9.NSTrafficAssistant.repository.PricelistRepositoryIntegrationTest;
import com.team9.NSTrafficAssistant.repository.TicketRepositoryIntegrationTest;
import com.team9.NSTrafficAssistant.service.PricelistItemServiceUnitTest;
import com.team9.NSTrafficAssistant.service.PricelistServiceIntegrationTest;
import com.team9.NSTrafficAssistant.service.TicketServiceIntegrationTest;
import com.team9.NSTrafficAssistant.service.TicketServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	PricelistItemServiceUnitTest.class, TicketServiceUnitTest.class,
	PricelistRepositoryIntegrationTest.class, PricelistItemRepositoryIntegrationTest.class, TicketRepositoryIntegrationTest.class,
	PricelistServiceIntegrationTest.class, TicketServiceIntegrationTest.class
})
public class TestSuits {

}
