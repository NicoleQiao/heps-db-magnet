/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.magnet.jpa;

import heps.db.magnet.entity.MagnetDesignTable;
import heps.db.magnet.entity.DesignOthersTable;
import heps.db.magnet.entity.MagnetDesignParameterTable;
import heps.db.magnet.entity.MagnetDesignRequirementTable;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author qiaoys
 */
public class DesignAPI {

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    String result;
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("heps-db-magnetPU");
//    EntityManager em = emf.createEntityManager();
//    EntityTransaction et = em.getTransaction();

    MagnetDesignTable design = new MagnetDesignTable();

    public static Double precalc(Object obj) {
        if (obj.toString().isEmpty()) {
            return null;
        } else {
            return Double.parseDouble(obj.toString());
        }
    }

    public void init() {
        emf = Persistence.createEntityManagerFactory("heps-db-magnetPU");
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    public void destroy() {
//et.commit();
        em.close();
        emf.close();
    }

    public void insertDesign(ArrayList designall, ArrayList design_requirement, ArrayList design_para, ArrayList design_plot, int other_flag, ArrayList design_others) {
//System.out.println(design_para);
        design.setType(designall.get(0).toString());
        design.setFamily(Integer.parseInt(designall.get(1).toString()));
        design.setDesignName(designall.get(0).toString() + "-" + Integer.parseInt(designall.get(1).toString()));
        design.setDesignBy(designall.get(2).toString());
        design.setApprovedBy(designall.get(3).toString());
        design.setRemark(designall.get(4).toString());
        em.persist(design);
        et.commit();

        et.begin();
        MagnetDesignRequirementTable require = new MagnetDesignRequirementTable();
        require.setDesignId(design.getDesignId());
        require.setLength(precalc(design_requirement.get(0)));
        require.setAperture(precalc(design_requirement.get(1).toString()));
        require.setMinimumGap(precalc(design_requirement.get(2).toString()));
        require.setUsefulField(precalc(design_requirement.get(3).toString()));
        require.setIntensityB(precalc(design_requirement.get(4).toString()));
        require.setIntensityQ(precalc(design_requirement.get(5).toString()));
        require.setIntensityS(precalc(design_requirement.get(6).toString()));
        require.setIntensityO(precalc(design_requirement.get(7).toString()));
        require.setSystemComponent(precalc(design_requirement.get(8).toString()));
        require.setNonSystemComponent(precalc(design_requirement.get(9).toString()));
        em.persist(require);
        et.commit();

        et.begin();
        MagnetDesignParameterTable parameter = new MagnetDesignParameterTable();
        parameter.setDesignId(design.getDesignId());
        parameter.setOffset(precalc(design_para.get(0).toString()));
        parameter.setAmpereTurns(precalc(design_para.get(1).toString()));
        parameter.setAmpereTurnsEach(precalc(design_para.get(2).toString()));
        parameter.setCur(precalc(design_para.get(3).toString()));
        parameter.setWire(design_para.get(4).toString());
        parameter.setCurrentDensity(precalc(design_para.get(5).toString()));
        parameter.setWireLength(precalc(design_para.get(6).toString()));
        parameter.setResistance(precalc(design_para.get(7).toString()));
        parameter.setInductance(precalc(design_para.get(8).toString()));
        parameter.setVoltage(precalc(design_para.get(9).toString()));
        parameter.setConsumption(precalc(design_para.get(10).toString()));
        parameter.setCPressureDrop(precalc(design_para.get(11).toString()));
        parameter.setCChannelNum(precalc(design_para.get(12).toString()));
        parameter.setCVelocity(precalc(design_para.get(13).toString()));
        parameter.setCFlow(precalc(design_para.get(14).toString()));
        parameter.setCTemp(precalc(design_para.get(15).toString()));
        parameter.setCoreLength(precalc(design_para.get(16).toString()));
        parameter.setCoreSection(precalc(design_para.get(17).toString()));
        parameter.setCoreWeight(precalc(design_para.get(18).toString()));
        parameter.setCopperWeight(precalc(design_para.get(19).toString()));
        //design_plot
        parameter.setPhysicsPlot(design_plot.get(0).toString());
        parameter.setMechanicalPlot(design_plot.get(1).toString());
        em.persist(parameter);
        et.commit();

        et.begin();
        for (int i = 0; i < design_others.size(); i = i + 3) {
            DesignOthersTable others = new DesignOthersTable();
            others.setDesignId(design);
            others.setProperty(design_others.get(i).toString());
            if (design_others.get(i + 1).toString().equals("text")) {
                others.setValueText(design_others.get(i + 2).toString());
            } else {
                others.setValueNum(precalc(design_others.get(i + 2).toString()));
            }
            em.persist(others);
        }
        et.commit();
    }

    public String queryDesignAll() {
        Query query = em.createNamedQuery("MagnetDesignTable.findAll");
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();
    }

    public String queryDesignById(Integer designid) {
        Query query = em.createNamedQuery("MagnetDesignTable.findByDesignId");
        query.setParameter("designId", designid);
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();
    }

    public String queryDesignByType(String type) {
        Query query = em.createNamedQuery("MagnetDesignTable.findByType");
        query.setParameter("type", type);
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();
    }

    public String queryDesignByFamily(Integer family) {
        Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family= :family");
        query.setParameter("family", family);
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();
    }

    public String queryDesignByTypeFamily(String type, Integer family) {
        Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND m.family= :family");
        query.setParameter("type", type).setParameter("family", family);
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();
    }

    public String queryDesignbyLength(Double lengthmin, Double lengthmax) {
//        if (lengthmin == null) {
//            Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.magnetDesignRequirementTable.length <= :lengthmax ");
//            query.setParameter("lengthmax", lengthmax);
//            List<MagnetDesignTable> re = query.getResultList();
//            return re.toString();
//        } else if (lengthmax == null) {
//            Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.magnetDesignRequirementTable.length >= :lengthmin ");
//            query.setParameter("lengthmin", lengthmin);
//            List<MagnetDesignTable> re = query.getResultList();
//            return re.toString();
//        } else {
        if (lengthmin == null) {
            lengthmin = 0.0;
        }
        if (lengthmax == null) {
            lengthmax = Double.MAX_VALUE;
        }
        Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax");
        query.setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax);
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();
        // }
    }

    public String queryDesignbyLengthIntensity(Double lengthmin, Double lengthmax, Integer intensity, Double intensitymin, Double intensitymax) {
        String res;
        if (intensitymin == null) {
            intensitymin = 0.0;
        }
        if (intensitymax == null) {
            intensitymax = Double.MAX_VALUE;
        }
        if (lengthmin == null) {
            lengthmin = 0.0;
        }
        if (lengthmax == null) {
            lengthmax = Double.MAX_VALUE;
        }
        switch (intensity) {
            case 1: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityB BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 2: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityQ BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 3: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityS BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 4: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityO BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            default:
                res = "";
        }
        return res;
    }

    public String queryDesignbyTypeLengthIntensity(String type, Double lengthmin, Double lengthmax, Integer intensity, Double intensitymin, Double intensitymax) {
        String res;
        if (intensitymin == null) {
            intensitymin = 0.0;
        }
        if (intensitymax == null) {
            intensitymax = Double.MAX_VALUE;
        }
        if (lengthmin == null) {
            lengthmin = 0.0;
        }
        if (lengthmax == null) {
            lengthmax = Double.MAX_VALUE;
        }
        switch (intensity) {
            case 1: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityB BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("type", type).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 2: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityQ BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 3: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityS BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("type", type).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 4: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityO BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("type", type).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            default:
                res = "";
        }
        return res;
    }

    public String queryDesignbyFamilyLengthIntensity(Integer family, Double lengthmin, Double lengthmax, Integer intensity, Double intensitymin, Double intensitymax) {
        String res;
        if (intensitymin == null) {
            intensitymin = 0.0;
        }
        if (intensitymax == null) {
            intensitymax = Double.MAX_VALUE;
        }
        if (lengthmin == null) {
            lengthmin = 0.0;
        }
        if (lengthmax == null) {
            lengthmax = Double.MAX_VALUE;
        }
        switch (intensity) {
            case 1: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family = :family AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityB BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("family", family).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 2: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family = :family AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityQ BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 3: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family = :family AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityS BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("family", family).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 4: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family = :family AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityO BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("family", family).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            default:
                res = "";
        }
        return res;
    }

    public String queryDesignbyTypeFamilyLengthIntensity(String type, Integer family, Double lengthmin, Double lengthmax, Integer intensity, Double intensitymin, Double intensitymax) {
        String res;
        if (intensitymin == null) {
            intensitymin = 0.0;
        }
        if (intensitymax == null) {
            intensitymax = Double.MAX_VALUE;
        }
        if (lengthmin == null) {
            lengthmin = 0.0;
        }
        if (lengthmax == null) {
            lengthmax = Double.MAX_VALUE;
        }
        switch (intensity) {
            case 1: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND  m.family = :family AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityB BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("type", type).setParameter("family", family).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 2: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE  m.type = :type AND m.family = :family AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityQ BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("type", type).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 3: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE  m.type = :type AND m.family = :family AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityS BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("type", type).setParameter("family", family).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 4: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE  m.type = :type AND m.family = :family AND (m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax) AND (m.magnetDesignRequirementTable.intensityO BETWEEN :intensitymin AND :intensitymax)");
                query.setParameter("type", type).setParameter("family", family).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            default:
                res = "";
        }
        return res;
    }

    public String queryDesignbyIntensity(Integer intensity, Double intensitymin, Double intensitymax) {
        String res;
        if (intensitymin == null) {
            intensitymin = 0.0;
        }
        if (intensitymax == null) {
            intensitymax = Double.MAX_VALUE;
        }
        switch (intensity) {
            case 1: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.magnetDesignRequirementTable.intensityB BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 2: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.magnetDesignRequirementTable.intensityQ BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 3: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.magnetDesignRequirementTable.intensityS BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 4: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.magnetDesignRequirementTable.intensityO BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            default:
                res = "";
        }
        return res;
    }

    public String queryDesignbyTypeIntensity(String type, Integer intensity, Double intensitymin, Double intensitymax) {
        String res;
        if (intensitymin == null) {
            intensitymin = 0.0;
        }
        if (intensitymax == null) {
            intensitymax = Double.MAX_VALUE;
        }
        switch (intensity) {
            case 1: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND m.magnetDesignRequirementTable.intensityB BETWEEN :intensitymin AND :intensitymax ");
                query.setParameter("type", type).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 2: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND m.magnetDesignRequirementTable.intensityQ BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("type", type).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 3: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND m.magnetDesignRequirementTable.intensityS BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("type", type).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 4: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE  m.type = :type AND m.magnetDesignRequirementTable.intensityO BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("type", type).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            default:
                res = "";
        }
        return res;
    }

    public String queryDesignbyFamilyIntensity(Integer family, Integer intensity, Double intensitymin, Double intensitymax) {
        String res;
        if (intensitymin == null) {
            intensitymin = 0.0;
        }
        if (intensitymax == null) {
            intensitymax = Double.MAX_VALUE;
        }
        switch (intensity) {
            case 1: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family = :family AND m.magnetDesignRequirementTable.intensityB BETWEEN :intensitymin AND :intensitymax ");
                query.setParameter("family", family).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 2: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family = :family AND m.magnetDesignRequirementTable.intensityQ BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("family", family).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 3: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family = :family AND m.magnetDesignRequirementTable.intensityS BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("family", family).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 4: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE  m.family = :family AND m.magnetDesignRequirementTable.intensityO BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("family", family).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            default:
                res = "";
        }
        return res;
    }

    public String queryDesignbyTypeFamilyIntensity(String type, Integer family, Integer intensity, Double intensitymin, Double intensitymax) {
        String res;
        if (intensitymin == null) {
            intensitymin = 0.0;
        }
        if (intensitymax == null) {
            intensitymax = Double.MAX_VALUE;
        }
        switch (intensity) {
            case 1: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family = :family AND m.type = :type AND m.magnetDesignRequirementTable.intensityB BETWEEN :intensitymin AND :intensitymax ");
                query.setParameter("type", type).setParameter("family", family).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 2: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE  m.type = :type AND m.family = :family AND m.magnetDesignRequirementTable.intensityQ BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("type", type).setParameter("family", family).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 3: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND m.family = :family AND m.magnetDesignRequirementTable.intensityS BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("type", type).setParameter("family", family).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            case 4: {
                Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE  m.type = :type AND m.family = :family AND m.magnetDesignRequirementTable.intensityO BETWEEN :intensitymin AND :intensitymax");
                query.setParameter("type", type).setParameter("family", family).setParameter("intensitymin", intensitymin).setParameter("intensitymax", intensitymax);
                List<MagnetDesignTable> re = query.getResultList();
                res = re.toString();
            }
            break;
            default:
                res = "";
        }
        return res;
    }

    public String queryDesignbyTypeLength(String type, Double lengthmin, Double lengthmax) {
        if (lengthmin == null) {
            lengthmin = 0.0;
        }
        if (lengthmax == null) {
            lengthmax = Double.MAX_VALUE;
        }
        Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax");
        query.setParameter("type", type).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax);
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();

    }

    public String queryDesignbyFamilyLength(Integer family, Double lengthmin, Double lengthmax) {
        if (lengthmin == null) {
            lengthmin = 0.0;
        }
        if (lengthmax == null) {
            lengthmax = Double.MAX_VALUE;
        }
        Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.family= :family AND m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax");
        query.setParameter("family", family).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax);
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();

    }

    public String queryDesignbyTypeFamilyLength(String type, Integer family, Double lengthmin, Double lengthmax) {
        if (lengthmin == null) {
            lengthmin = 0.0;
        }
        if (lengthmax == null) {
            lengthmax = Double.MAX_VALUE;
        }
        Query query = em.createQuery("SELECT m FROM MagnetDesignTable m WHERE m.type = :type AND m.family= :family AND m.magnetDesignRequirementTable.length BETWEEN :lengthmin AND :lengthmax");
        query.setParameter("type", type).setParameter("family", family).setParameter("lengthmin", lengthmin).setParameter("lengthmax", lengthmax);
        List<MagnetDesignTable> re = query.getResultList();
        return re.toString();

    }

    public Integer deleteDesignById(Integer designId) {
        // et.begin();
        MagnetDesignTable demag = em.find(MagnetDesignTable.class, designId);
        //Query query = em.createNamedQuery("MagnetDesignTable.findByDesignId");
        //query.setParameter("designId", designId);
        //MagnetDesignTable re = (MagnetDesignTable)query.getSingleResult();
        // System.out.println(re);
        em.remove(demag);
        et.commit();
        //em.close();
        //emf.close();
        return 1;
    }

    public String queryMplot(Integer designId) {
        String mplot = em.find(MagnetDesignTable.class, designId).getMagnetDesignParameterTable().getMechanicalPlot();
        return mplot;
    }
 public String queryPplot(Integer designId) {
        String pplot = em.find(MagnetDesignTable.class, designId).getMagnetDesignParameterTable().getPhysicsPlot();
        return pplot;
    }
    public String queryDesignOthers(Integer designId) {
        Collection<DesignOthersTable> userdefine = em.find(MagnetDesignTable.class, designId).getDesignOthersTableCollection();
        //System.out.println(Arrays.toString(userdefine.toArray()));
        return Arrays.toString(userdefine.toArray());
    }

    public String queryAllTypes() {
        String re;
        Query query = em.createQuery("SELECT DISTINCT m.type from MagnetDesignTable m");
        List s = query.getResultList();
        re = s.toString().substring(1, s.toString().length() - 1);
        return re;
    }

    public String queryAllFamilys() {
        String re;
        Query query = em.createQuery("SELECT DISTINCT m.family from MagnetDesignTable m");
        List s = query.getResultList();
        re = s.toString().substring(1, s.toString().length() - 1);
//System.out.println(re);
        return re;
    }
}
