# -*- coding: utf-8 -*-
"""
Django settings for testAdmin project.

For more information on this file, see
https://docs.djangoproject.com/en/1.6/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/1.6/ref/settings/
"""

# Build paths inside the project like this: os.path.join(BASE_DIR, ...)
import os
BASE_DIR = os.path.dirname(os.path.dirname(__file__))


# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/1.6/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = 'jm25tth14@x=!=_y8y9fuz9)t3ucs(^4%_y&_h7l$r=kdy&=^p'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

TEMPLATE_DEBUG = True

ALLOWED_HOSTS = []


# Application definition

INSTALLED_APPS = (
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'south',
    'itest',
)

MIDDLEWARE_CLASSES = (
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
)

ROOT_URLCONF = 'testAdmin.urls'

WSGI_APPLICATION = 'testAdmin.wsgi.application'


# Database
# https://docs.djangoproject.com/en/1.6/ref/settings/#databases

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql_psycopg2',
        'NAME': 'itest',
        'USER': 'postgres',
        'PASSWORD': 'xcy123',
        'HOST': '127.0.0.1',
        'PORT': '5432',
    }
}

# Internationalization
# https://docs.djangoproject.com/en/1.6/topics/i18n/

LANGUAGE_CODE = 'zh-cn'

TIME_ZONE = 'Asia/Shanghai'

USE_I18N = True

USE_L10N = True

USE_TZ = True


# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/1.6/howto/static-files/

STATIC_URL = '/static/'
# 新增
# 登录成功跳转页面
LOGIN_REDIRECT_URL = '/'
DATETIME_FORMAT = 'Y-m-d H:i:s'
DATE_FORMAT = 'Y-m-d'
SHORT_DATE_FORMAT = 'Y-m-d'
TIME_FORMAT = 'H:i:s'
STATICFILES_DIRS = (
    BASE_DIR + '/static/',
        )

LOGGING = {
'version': 1,
'formatters': {
    'verbose': {
        'format': '%(levelname)s %(asctime)s %(module)s %(process)d %(thread)d %(message)s'
        },
    'simple': {
        'format': '%(levelname)s %(message)s'
        },
    },
'handlers': {
    'null': {
        'level': 'DEBUG',
        'class': 'logging.NullHandler',
        },
    'console':{
        'level': 'DEBUG',
        'class': 'logging.StreamHandler',
        'formatter': 'simple'
        },
    'mail_admins': {
        'level': 'ERROR',
        'class': 'django.utils.log.AdminEmailHandler',
        }
    },
'loggers': {
    'django': {
        'handlers': ['console'],
        'level': 'INFO',
        },
    'itest.management': {
        'handlers': ['console'],
        'level': 'DEBUG',
        }
    }
}
