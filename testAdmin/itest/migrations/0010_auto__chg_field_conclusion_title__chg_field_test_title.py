# -*- coding: utf-8 -*-
from south.utils import datetime_utils as datetime
from south.db import db
from south.v2 import SchemaMigration
from django.db import models


class Migration(SchemaMigration):

    def forwards(self, orm):

        # Changing field 'Conclusion.title'
        db.alter_column(u'itest_conclusion', 'title', self.gf('django.db.models.fields.CharField')(max_length=250))

        # Changing field 'Test.title'
        db.alter_column(u'itest_test', 'title', self.gf('django.db.models.fields.CharField')(max_length=250))

    def backwards(self, orm):

        # Changing field 'Conclusion.title'
        db.alter_column(u'itest_conclusion', 'title', self.gf('django.db.models.fields.CharField')(max_length=150))

        # Changing field 'Test.title'
        db.alter_column(u'itest_test', 'title', self.gf('django.db.models.fields.CharField')(max_length=150))

    models = {
        'itest.conclusion': {
            'Meta': {'object_name': 'Conclusion'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'test': ('django.db.models.fields.related.ForeignKey', [], {'related_name': "'conclusions'", 'to': "orm['itest.Test']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250'})
        },
        'itest.tag': {
            'Meta': {'object_name': 'Tag'},
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'word': ('django.db.models.fields.CharField', [], {'max_length': '35'})
        },
        'itest.test': {
            'Meta': {'object_name': 'Test'},
            'content': ('django.db.models.fields.TextField', [], {'max_length': '5850', 'null': 'True', 'blank': 'True'}),
            'create_at': ('django.db.models.fields.DateTimeField', [], {'default': 'datetime.datetime.now'}),
            u'id': ('django.db.models.fields.AutoField', [], {'primary_key': 'True'}),
            'num': ('django.db.models.fields.IntegerField', [], {'null': 'True', 'blank': 'True'}),
            'summary': ('django.db.models.fields.CharField', [], {'max_length': '450', 'null': 'True', 'blank': 'True'}),
            'tags': ('django.db.models.fields.related.ManyToManyField', [], {'related_name': "'tests'", 'symmetrical': 'False', 'to': "orm['itest.Tag']"}),
            'title': ('django.db.models.fields.CharField', [], {'max_length': '250'})
        }
    }

    complete_apps = ['itest']