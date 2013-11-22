package shared.model;

import org.w3c.dom.Element;
import shared.DataImporter;

/**
 * User: matt nielson
 * Date: 10/12/13
 * Time: 9:47 AM
 */
public class RecordDTO {

	private int id;
	private int batchId;
	private String value;
	private int position;
	private int recordNumber;

	public RecordDTO(int batchId, Element value, int position, int recordNumber) {
		this.id = -1;
		this.batchId = batchId;
		this.value = DataImporter.getValue(value);
		this.position = position;
		this.recordNumber = recordNumber;
	}

	public RecordDTO(int id, int batchId, String value, int position, int recordNumber) {
		this.id = id;
		this.batchId = batchId;
		this.value = value;
		this.position = position;
		this.recordNumber = recordNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBatchId() {
		return batchId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getPosition() {
		return position;
	}

	public int getRecNumber() {
		return recordNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RecordDTO recordDTO = (RecordDTO) o;

		if (id != recordDTO.id) return false;
		if (batchId != recordDTO.batchId) return false;
		if (value != null ? !value.equals(recordDTO.value) : recordDTO.value != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + batchId;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

}
