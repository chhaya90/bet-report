package org.paddy.power.database.writer;

import java.util.List;

import org.paddy.power.dto.ReportDao;

public interface Writer {
    void write();

    void writeTotalLiabilityReport();

    void setList(List<ReportDao> list);
}
