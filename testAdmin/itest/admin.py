# -*- coding: UTF-8 -*-
from django.contrib import admin
from common import register, BaseAdmin
from models import *

@register(Tag)
class TagAdmin(BaseAdmin):
    search_fields = ['word']
    list_display = ('word',)
    fieldsets = [
        (
            None,
            {'fields': ['word']},
        ),
    ]

class QuestionInline(admin.TabularInline):
    readonly_fields = ('create_at', )
    can_delete = False
    extra = 0
    model = Question
class ConclusionInline(admin.TabularInline):
    readonly_fields = ('create_at', )
    can_delete = False
    extra = 0
    model = Conclusion
@register(Test)
class TestAdmin(BaseAdmin):
    '图书管理'
    inlines = [QuestionInline, ConclusionInline, ]
    search_fields = ['title']
    date_hierarchy = 'create_at'
    list_filter = ['star', ]
    list_display = ('title', 'num', 'star', 'create_at')
    readonly_fields = ('create_at', )
    fieldsets = [
        (
            None,
            {'fields': ['num', 'title', 'tags', 'star', 'type', 'summary', 'content']},
        ),
        (
            '其他',
            {
                'fields': ['create_at', ],
                'classes': ['collapse'],
            },
        ),
    ]

@register(Conclusion)
class ConclusionAdmin(BaseAdmin):
    '结论管理'
    #inlines = [StarInline, BastInline, ]
    search_fields = ['title']
    date_hierarchy = 'create_at'
    list_filter = ['test', ]
    list_display = ('title', 'num', 'test', 'create_at')
    readonly_fields = ('create_at', 'test', )
    fieldsets = [
        (
            None,
            {'fields': ['num', 'title', 'test', 'summary', 'content']},
        ),
        (
            '其他',
            {
                'fields': ['create_at', ],
                'classes': ['collapse'],
            },
        ),
    ]
class AnswerInline(admin.TabularInline):
    readonly_fields = ('create_at', )
    fk_name = 'question'
    can_delete = False
    extra = 0
    model = Answer
@register(Question)
class QuestionAdmin(BaseAdmin):
    '问题管理'
    inlines = [AnswerInline, ]
    search_fields = ['title']
    date_hierarchy = 'create_at'
    #list_filter = ['tag', ]
    list_display = ('title', 'num', 'test', 'create_at')
    readonly_fields = ('create_at', 'test', )
    fieldsets = [
        (
            None,
            {'fields': ['num', 'title', 'test', 'summary', 'content']},
        ),
        (
            '其他',
            {
                'fields': ['create_at', ],
                'classes': ['collapse'],
            },
        ),
    ]
@register(Answer)
class AnswerAdmin(BaseAdmin):
    '答案管理'
    #inlines = [StarInline, BastInline, ]
    search_fields = ['title']
    date_hierarchy = 'create_at'
    #list_filter = ['tag', ]
    list_display = ('title', 'num', 'jump', 'conclusion', 'question', 'create_at')
    readonly_fields = ('create_at', 'question', )
    fieldsets = [
        (
            None,
            {'fields': ['num', 'title', 'question', 'jump', 'conclusion','summary', 'content']},
        ),
        (
            '其他',
            {
                'fields': ['create_at', ],
                'classes': ['collapse'],
            },
        ),
    ]
