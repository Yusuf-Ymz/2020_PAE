package be.ipl.pae.bizz.bizz;

import be.ipl.pae.annotation.Inject;
import be.ipl.pae.bizz.ucc.PhotoUcc;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.PhotoDao;

public class PhotoUccImpl implements PhotoUcc {
  @Inject
  private PhotoDao photoDao;

  @Inject
  private DalServices dal;


}
