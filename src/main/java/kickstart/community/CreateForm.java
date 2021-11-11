package kickstart.community;

import org.salespointframework.useraccount.Password;

import javax.validation.constraints.NotEmpty;

public interface CreateForm {



	public String getName();

	public String getPassword();
}