from django import forms

from .models import NewsItem


class PostForm(forms.ModelForm):

        class Meta:
            model = NewsItem
            fields = ('title', 'description', 'publish_date')
