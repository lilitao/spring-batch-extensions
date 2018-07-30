package org.springframework.batch.item.excel.support.rowset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.excel.Sheet;
import org.springframework.util.StringUtils;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RowNumberColumnNameExtractorTest {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @InjectMocks
    RowNumberColumnNameExtractor rowNumberColumnNameExtractor;

    @Mock
    Sheet sheet;

    @Test
    public void should_get_column_name() {


    }
}