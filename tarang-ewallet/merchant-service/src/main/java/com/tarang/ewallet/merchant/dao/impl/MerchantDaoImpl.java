package com.tarang.ewallet.merchant.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.tarang.ewallet.dto.UserDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.merchant.dao.MerchantDao;
import com.tarang.ewallet.merchant.util.MerchantUserCriteriaUtil;
import com.tarang.ewallet.model.BusinessInformation;
import com.tarang.ewallet.model.BusinessOwnerInformation;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.util.QueryFilter;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class MerchantDaoImpl implements MerchantDao {

	private static final Logger LOGGER = Logger.getLogger(MerchantDaoImpl.class);

	private HibernateOperations hibernateOperations;
	
	private static final String UNIQUE_EX = "unique.address.exception";
	
	private static final String MERCHANT_ID = "getMerchantIdById";
	
	private static final String MERCHANT_BY_PHONE = "getBusinessInformationByPhoneId";

	private static final String LEGAL_NAME_QUERY = "select b.legalName from BusinessInformation b, Merchant m, Authentication a " +
			"where a.id = m.authenticationId and m.businessInformationId = b.id and a.emailId = ?";

	private static final String MERCHANT_CODE_QUERY = "select b.merchantcode from BusinessInformation b, Merchant m, Authentication a " +
			"where a.id = m.authenticationId and m.businessInformationId = b.id and a.emailId = ?";
	
	public MerchantDaoImpl(HibernateOperations hibernateOperations) {
		this.hibernateOperations = hibernateOperations;
	}

	@Override
	public BusinessInformation saveBusinessInformation(
			BusinessInformation businessInformation) throws WalletException {
		hibernateOperations.save(businessInformation);
		return businessInformation;
	}

	@Override
	public BusinessOwnerInformation saveBusinessOwnerInformation(
			BusinessOwnerInformation businessOwnerInformation)
			throws WalletException {
		hibernateOperations.save(businessOwnerInformation);
		return businessOwnerInformation;
	}

	@Override
	public Merchant saveMerchant(Merchant merchant) throws WalletException {
		hibernateOperations.save(merchant);
		return merchant;
	}

	@Override
	public List<UserDto> getMerchantsList(final QueryFilter qf,
			final String fromDate, final String toDate, final String name, final String emailId, final Long status)
			throws WalletException {
		List list = hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Integer total = getMerchantCount(qf, fromDate, toDate, name, emailId, status);
				MerchantUserCriteriaUtil.updateCount(total, qf);
				String hql = MerchantUserCriteriaUtil.getMerchantSearchQuery(
						qf, fromDate, toDate, name, emailId, status);
				Query q = session.createQuery(hql);
				q.setFirstResult((qf.getPage() - 1) * qf.getRows());
				q.setMaxResults(qf.getRows());
				return q.list();
			}
		});

		return list;
	}

	private Integer getMerchantCount(final QueryFilter qf,
			final String fromDate, final String toDate, final String name, final String emailId, final Long status) {
		return Integer.valueOf(hibernateOperations
				.find(MerchantUserCriteriaUtil.getMerchantSearchCountQuery(qf,
						fromDate, toDate, name, emailId, status)).get(0).toString());
	}

	@Override
	public BusinessInformation getBusinessInformation(Long id)
			throws WalletException {
		List<BusinessInformation> list = (List<BusinessInformation>) hibernateOperations
				.findByNamedQuery("getBusinessInformationById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			throw new WalletException(UNIQUE_EX);
		}
	}

	@Override
	public void updateBusinessInformation(
			BusinessInformation businessInformation) throws WalletException {
		hibernateOperations.update(businessInformation);
	}

	@Override
	public BusinessOwnerInformation getBusinessOwnerInformation(Long id)
			throws WalletException {
		List<BusinessOwnerInformation> list = (List<BusinessOwnerInformation>) hibernateOperations
				.findByNamedQuery("getBusinessOwnerInformationById", id);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			throw new WalletException(UNIQUE_EX);
		}
	}

	@Override
	public void updateBusinessOwnerInformation(
			BusinessOwnerInformation businessOwnerInformation)
			throws WalletException {
		hibernateOperations.update(businessOwnerInformation);
	}

	@Override
	public Merchant getMerchant(Long id) throws WalletException {
		try {
			List<Merchant> list = (List<Merchant>) hibernateOperations
					.find("from Merchant as merchant where merchant.id=" + id);
			if (list != null && list.size() == 1) {
				return list.get(0);
			} else {
				throw new WalletException(UNIQUE_EX);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public void updateMerchant(Merchant merchant) throws WalletException {
		hibernateOperations.update(merchant);
	}

	@Override
	public Long getMerchantId(Long authenticationId, String userType)
			throws WalletException {
		List<Merchant> list = (List<Merchant>) hibernateOperations
				.findByNamedQuery(MERCHANT_ID, authenticationId);
		if (list != null && list.size() == 1) {
			return list.get(0).getId();
		} else {
			throw new WalletException(UNIQUE_EX);
		}
	}

	@Override
	public Merchant getMerchantByAuthId(Long authId) throws WalletException {
		List<Merchant> list = (List<Merchant>) hibernateOperations.findByNamedQuery(MERCHANT_ID, authId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String getLegalName(final String email) {
		List list = hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(LEGAL_NAME_QUERY);
				q.setParameter(0, email);
				return q.list();
			}
		});
		if(list != null && list.size() > 0) {
			return (String)list.get(0);
		}
		return null;
	}

	@Override
	public String getMerchantCode(final String email) {
		List list = hibernateOperations.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query q = session.createSQLQuery(MERCHANT_CODE_QUERY);
				q.setParameter(0, email);
				return q.list();
			}
		});
		if(list != null && list.size() > 0) {
			return (String)list.get(0);
		}
		return null;
	}

	@Override
	public MerchantPayInfo saveMerchantPayInfo(MerchantPayInfo merchantPayInfo)
			throws WalletException {
		hibernateOperations.save(merchantPayInfo);
		return merchantPayInfo;
	}
	
	@Override
	public MerchantPayInfo getMerchantPayInfo(Long merchantPayInfoId) throws WalletException {
		List<MerchantPayInfo> list = (List<MerchantPayInfo>) hibernateOperations.findByNamedQuery("getMerchantPayInfoById", merchantPayInfoId);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			throw new WalletException(UNIQUE_EX);
		}
	}

	@Override
	public void updateMerchantPayInfo(MerchantPayInfo merchantPayInfo) throws WalletException {
			hibernateOperations.update(merchantPayInfo);
	}

	@Override
	public void deleteMerchantPayInfo(MerchantPayInfo merchantPayInfo) throws WalletException {
		hibernateOperations.delete(merchantPayInfo);
	}

	@Override
	public Merchant getMerchantByPhoneId(Long phoneId) throws WalletException {
		BusinessInformation businessInformation = null;
		List<BusinessInformation> list1 = (List<BusinessInformation>) hibernateOperations.findByNamedQuery(MERCHANT_BY_PHONE, phoneId);
		if (list1 != null && list1.size() == 1) {
			businessInformation = list1.get(0);
		} else {
			return null;
		}
		List<Merchant> list2 = (List<Merchant>) hibernateOperations
				.find("from Merchant as merchant where merchant.businessInformation=" + businessInformation.getId());
		if (list2 != null && list2.size() == 1) {
			return list2.get(0);
		}else {
			return null;
		}
	}
}
