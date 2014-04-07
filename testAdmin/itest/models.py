# -*- coding: utf-8 -*-
import logging
log = logging.getLogger(__name__)
from django.db import models
from common.models import BaseModel, UserModel

class Item(BaseModel):
    '条目'
    num = models.IntegerField(
            blank=True, null=True,
            verbose_name='序号'
            )
    title = models.CharField(
            blank=True, null=True,
            max_length=250,
            verbose_name='标题',
            )
    summary = models.CharField(
            blank=True, null=True,
            max_length=450,
            verbose_name='摘要',
            )
    content = models.TextField(
            blank=True, null=True,
            max_length=5850,
            verbose_name='内容',
            )
    def __unicode__(self):
        ret = []
        if hasattr(self, 'test'):
            ret.append(unicode(self.test.num))
        if self.num:
            ret.append(unicode(self.num))
        if self.title:
            ret.append(self.title)
        ret.append(unicode(self.id))
        return u':'.join(ret)
    class Meta:
        abstract = True

class Tag(models.Model):
    '标签'
    word = models.CharField(
            max_length=35,
            verbose_name='标签',
            )
    @staticmethod
    def add(word):
        '增加'
        if Tag.objects.filter(word=word).count() == 0:
            t = Tag()
            t.word = word
            t.save()
            log.debug('save tag: %s'%word)
            return t
        return Tag.objects.filter(word=word).all()[0]

    def __unicode__(self):
        return self.word
    class Meta:
        verbose_name = '标签'
        verbose_name_plural = '标签'
        app_label = 'itest'

TYPE = (
        ('JUMP', '跳转'),
        ('POINT', '积分'),
        )
class Test(Item):
    '测试'
    tags = models.ManyToManyField(Tag, related_name='tests')
    star = models.IntegerField(
            default = 0,
            verbose_name='星数'
            )
    type = models.CharField(
            default='JUMP',
            choices=TYPE,
            verbose_name='类型',
            max_length=5,
            )


    class Meta:
        verbose_name = '测试'
        verbose_name_plural = '测试'
        app_label = 'itest'
        ordering = ['star', ]

class Conclusion(Item):
    '测试'
    test = models.ForeignKey(Test,
            related_name='conclusions',
            verbose_name='测试'
            )

    class Meta:
        verbose_name = '结论'
        verbose_name_plural = '结论'
        app_label = 'itest'
        ordering = ['test', 'num', ]
class Question(Item):
    '问题'
    test = models.ForeignKey(Test,
            related_name='questions',
            verbose_name='测试'
            )
    class Meta:
        verbose_name = '问题'
        verbose_name_plural = '问题'
        app_label = 'itest'
        ordering = ['test', 'num', ]

class Answer(Item):
    '答案'
    question = models.ForeignKey(Question,
            related_name='answers',
            verbose_name='问题'
            )
    jump = models.ForeignKey(Question,
            blank=True, null=True,
            related_name='+',
            verbose_name='跳转'
            )
    conclusion = models.ForeignKey(Conclusion,
            blank=True, null=True,
            related_name='+',
            verbose_name='结论'
            )

    class Meta:
        verbose_name = '答案'
        verbose_name_plural = '答案'
        app_label = 'itest'
        ordering = ['-jump', '-conclusion']
